import NotificationService from '../services/notification';

class NotificationController {
  static async createNotification(req, res, next) {
    try {
      const response = await NotificationService.createNotification(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getNotification(req, res, next) {
    try {
      const response = await NotificationService.getNotification(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllNotifications(req, res, next) {
    try {
      const response = await NotificationService.getAllNotifications(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default NotificationController;