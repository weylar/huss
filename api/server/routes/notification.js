import { Router } from 'express';
import NotificationController from '../controllers/notification';
import Auth from '../middleware/utils/Auth';

const { createNotification, getNotification, getAllNotifications } = NotificationController;
const { getUser } = Auth;

const notificationRouter = Router();

notificationRouter.post('/', getUser, createNotification);
notificationRouter.get('/:notificationId', getUser, getNotification);
notificationRouter.get('/', getUser, getAllNotifications);

export default notificationRouter;