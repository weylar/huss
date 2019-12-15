import Helper from '../middleware/utils/Helper';
import db from '../src/models';

class UserService {
  static async signUpUser(newUser) {
    const hashedpassword = Helper.hashPassword(newUser.password);
    newUser.password = hashedpassword;

    const user = await db.User.create(newUser);

    const { id, email, firstName, lastName, state, city, phoneNumber, profileImgUrl, isAdmin } = user;
    const payLoad = { id, email };
    const token = Helper.getToken(payLoad);
    return {
      status: 'success',
      statusCode: 201,
      data: {
        id,
        email,
        firstName,
        lastName,
        state,
        city,
        phoneNumber,
        isAdmin,
        profileImageUrl: profileImgUrl,
        token,
      },
      message: 'You have successfully signed up on Huss.ng',
    };
  }

  static async logInUser(userCredentials) {
    
    const { email, password } = userCredentials;
    const foundUser = await db.User.findOne({
      where: { email }
    });

    if (!foundUser) {
      return {
        status: 'error',
        statusCode: 401,
        message: 'This email is not registered here'
      };
    }

    const hash = foundUser.password;
    const isDeleted = foundUser.isDeleted;
    
    const isAdmin = foundUser.isAdmin;

    if(isDeleted === true) {
      return {
        status: 'error',
        statusCode: 422,
        message: 'User not found'
      }
    }
    if (Helper.comparePassword(password, hash) === true) {
      const { id, firstName, lastName } = foundUser;
      const payLoad = { id, email };
      const token = Helper.getToken(payLoad);
      return {
        status: 'success',
        statusCode: 200,
        data: {
          userId: id,
          email,
          firstName,
          lastName,
          isAdmin,
          token
        },
        message: `Welecome, ${firstName} ${lastName}`
      };
    }
    return {
      status: 'error',
      statusCode: 401,
      message: 'Authentication failed, invalid login details'
    };
  }

  static async addUserDetails(req) {

    const getAuser = await db.User.findOne({ where: {email: req.userEmail} });

    if(!getAuser) {
      return {
        status: 'error',
        statusCode: 404,
        message: "This email is not registered here"
      }
    }

    await db.User.update(
      { state: req.body.state, city: req.body.city, phoneNumber: req.body.phoneNumber },
      { where: { email: req.userEmail } }
    );
    return {
      statusCode: 202,
      status: 'success',
      message: 'User details added'
    };
  }

  static async logOutUser(req) {
    const getUser = await db.User.findOne({ where: {email : req.userEmail} });

    if(!getUser) {
      return {
        status: 'error',
        statusCode: 404,
        message: "This email is not registered here"
      }
    }

    const lastSeen = new Date();

    await db.User.update(
      { lastSeen: lastSeen },
      { where: { email: req.userEmail } }
    );

    return {
      statusCode: 200,
      status: 'success',
      message: 'User logged out successfully'
    };
  }

  static async userImage(req) {
    
    const getAuser = await db.User.findOne({ where: {email: req.userEmail} });

    if(!getAuser) {
      return {
        status: 'error',
        statusCode: 404,
        message: "This email is not registered here"
      }
    }

    await db.User.update(
      { profileImgUrl: req.body.profileImgUrl }, { where: { email: req.userEmail } }
    );

    return {
      statusCode: 202,
      status: 'success',
      message: 'Image successfully uploaded'
    };
  }
}

export default UserService;