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
}

export default UserService;