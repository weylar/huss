import { Router } from 'express';
import UserController from '../controllers/user';
import UserValidation from '../middleware/validations/user';
import Auth from '../middleware/utils/Auth';

const {
  signUpCheck
} = UserValidation;
const {
  signUpUser
} = UserController;
const { getUser } = Auth;

const userRouter = Router();

userRouter.post('/signup', signUpCheck, signUpUser);

export default userRouter;