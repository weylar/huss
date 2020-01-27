import UserService from '../services/user';
import EmailService from '../services/email';

class UserController {
  static async signUpUser(req, res, next) {
    try {
      const response = await UserService.signUpUser(req.body);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async logInUser(req, res, next) {
    try {
      const response = await UserService.logInUser(req.body);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async changePassword(req, res, next) {
    try {
      const response = await UserService.changePassword(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async addUserDetails(req, res, next) {
    try {
      const response = await UserService.addUserDetails(req, res);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async userImage(req, res, next) {
    try {
      const response = await UserService.userImage(req, res);
      return res.status(response.statusCode).json(response);
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
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async updateOnlineStatus(req, res, next) {
    try {
      const response = await UserService.updateOnlineStatus(req, res);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async deleteUser(req, res, next) {
    try {
      const response = await UserService.deleteUser(req, res);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAnotherUser(req, res, next) {
    try {
      const response = await UserService.getAnotherUser(req, res);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAnotherUserByEmail(req, res, next) {
    try {
      const response = await UserService.getAnotherUserByEmail(req, res);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getOwnUser(req, res, next) {
    try {
      const response = await UserService.getOwnUser(req, res);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllUsers(req, res, next) {
    try {
      const response = await UserService.getAllUsers();
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllUsersByLimit(req, res, next) {
    try {
      const response = await UserService.getAllUsersByLimit(req, res);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async paginateUsers(req, res, next) {
    try {
      const response = await UserService.paginateUsers(req, res);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getUsersLikeSuggest(req, res, next) {
    try {
      const response = await UserService.getUsersLikeSuggest(req, res);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default UserController;