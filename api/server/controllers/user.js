import UserService from '../services/user';

class UserController {
  static async signUpUser(req, res, next) {
    try {
      const response = await UserService.signUpUser(req.body);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default UserController;