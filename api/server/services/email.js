import jwt from "jsonwebtoken"
import bcrypt from "bcryptjs"
import db from "../src/models"
import {
  transporter,
  getPasswordResetURL,
  resetPasswordTemplate
} from "../middleware/utils/Email"

class EmailService {
    static async usePasswordHashToMakeToken({password, id: id, createdAt}){
        const secret = password + "-" + createdAt
        const token = jwt.sign({ id }, secret, {
          expiresIn: 3600 // 1 hour
        })
        return token;
      }

    static async sendPasswordResetEmail(res, email) {

        const  user = await db.User.findOne({ where: {email: email} });
        
        if(!user) {
            return {
                status: "error",
                statusCode: 403,
                message: "No user with such email address"
            }
        }
        const token1 = await EmailService.usePasswordHashToMakeToken(user);

        const url = getPasswordResetURL(user, token1);

        const emailTemplate = resetPasswordTemplate(user, url);
        
        transporter.sendMail(emailTemplate, (err, info) => {
            if (err) {
                return res.status(500).json({
                    status: 'error',
                    statusCode: 500,
                    error: err.message,
                    message: "Error sending email"
                })
            } else {
                return res.status(200).json({
                    status: 'success',
                    statusCode: 200,
                    message: 'A reset password link has been sent to your email'
                });
            }
        });
    }
      
    static async receiveNewPassword(req) {
        const { id, token } = req.params

        const { password, confirmPassword } = req.body
        
        if(!password && !confirmPassword) {
            return {
                status: "error",
                statusCode: 422,
                message: "Cannot be empty"
            }
        }

        const passwordPattern = /\w{6,}/g;

        if (!passwordPattern.test(password)) {
        return {
            status: 'error',
            statusCode: 406,
            error: 'Invalid password provided',
            message: 'Password must not be less than six(6) characters'
            };
        }
        
        if(password != confirmPassword) {
            return {
                status: "error",
                statusCode: 422,
                message: "Passwords do not match"
            }
        }
        
        const user = await db.User.findOne({ where: { id: id }});
        console.log(user)
        
        const secret = user.password + "-" + user.createdAt
        const payload = jwt.decode(token, secret)
        console.log(payload)
        if (payload.id === user.id) {
            const hash = bcrypt.hashSync(password, 10);
            db.User.update({ password: hash }, { where: {id: id} })
            return {
                status: "success",
                statusCode: 202,
                message: "Password successfully changed"
            }
        }

        return {
            status: "error",
            statusCode: 404,
            message: "Invalid user"
        }
    }
}

export default EmailService;