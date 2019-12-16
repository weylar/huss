import UserService from '../services/user';
import EmailService from '../services/email';
import db from '../src/models'
import {
  transporter,
  getPasswordResetURL,
  resetPasswordTemplate
} from "../middleware/utils/Email"

class UserController {
  static async signUpUser(req, res, next) {
    try {
      const response = await UserService.signUpUser(req.body);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async logInUser(req, res, next) {
    try {
      const response = await UserService.logInUser(req.body);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async addUserDetails(req, res, next) {
    try {
      const response = await UserService.addUserDetails(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async userImage(req, res, next) {
    try {
      const response = await UserService.userImage(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async sendPasswordResetEmail(req, res, next) {
    try {
      const { email } = req.params
      await EmailService.sendPasswordResetEmail(res, email);
    } catch (e) {
      return next(e);
    }
  }

  static async receiveNewPassword(req, res, next) {
    try {
      const response = await EmailService.receiveNewPassword(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async logOutUser(req, res, next) {
    try {
      const response = await UserService.logOutUser(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async deleteUser(req, res, next) {
    try {
      const response = await UserService.deleteUser(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAnotherUser(req, res, next) {
    try {
      const response = await UserService.getAnotherUser(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getOwnUser(req, res, next) {
    try {
      const response = await UserService.getOwnUser(req, res);
      return res.status(response.statusCode).send(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default UserController;