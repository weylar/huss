import db from '../src/models';

class NotificationService {
  static async createNotification(req) {
    req.body.userId = req.userId;
    const newNotification = await db.Notification.create(req.body);

    return {
      status: 'success',
      statusCode: 201,
      data: newNotification,
      message: 'Notification sent successfully'
    }
  }

  static async getNotification(req) {
    const notification = await db.Notification.findOne({ where: { id: req.params.notificationId } });

    if (!notification) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No such notification'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: notification,
      message: 'Notification retrieved successfully'
    }
  }

  static async getAllNotifications(req) {
    const notifications = await db.Notification.findAll({ where: { userId: req.userId } });

    return {
      status: 'success',
      statusCode: 200,
      data: notifications,
      message: 'All notifications retrived successfully'
    }
  }
}

export default NotificationService;