import { Router } from 'express';
import AdImageController from '../controllers/image';
import { adImageCheck } from '../middleware/validations/image';
import Auth from '../middleware/utils/Auth';

const { createAdImage
 } = AdImageController;
const { getUser } = Auth;

const adImageRouter = Router();

adImageRouter.post('/:adId/create', getUser, adImageCheck, createAdImage)

export default adImageRouter;