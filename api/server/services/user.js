import Helper from '../middleware/utils/Helper';
import db from '../src/models';

class UserService {
  static async signUpUser(newUser) {
    const hashedpassword = Helper.hashPassword(newUser.password);
    newUser.password = hashedpassword;

    const user = await db.User.create(newUser);

    const { id, email, firstName, lastName, state, city, phoneNumber, profileImageUrl } = user;
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
        profileImageUrl,
        token,
      },
      message: 'You have successfully signed up on Huss',
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
          token
        },
        message: `Wlecome, ${firstName} ${lastName}`
      };
    }
    return {
      status: 'error',
      statusCode: 401,
      message: 'Authentication failed, invalid login details'
    };
  }
}

export default UserService;