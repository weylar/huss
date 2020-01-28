import jwt from 'jsonwebtoken';
import dotenv from 'dotenv';

dotenv.config();

class Auth {
  static getUser(req, res, next) {
    try {
      if (req.headers.authorization) {
        const token = req.headers.authorization.split(' ')[1];
        const decoded = jwt.verify(token, process.env.SECRET);

        req.userId = decoded.id;
        req.userEmail = decoded.email;
        req.isAdmin = decoded.isAdmin;

        return next();
      } else {
        req.userId = null;
      }
    } catch (e) {
      return res.status(401).send({
        status: 'error',
        statusCode: 401,
        error: `${e.name}. ${e.message}`,
      });
    }
  }

  static adminCheck(req, res, next) {
    if (req.isAdmin !== true) {
      return res.status(401).json({
        status: 401,
        success: false,
        error: `You are not Authorized to perform this Action`
      });
    }
    return next();
  }
}

export default Auth;