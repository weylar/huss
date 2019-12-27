import { Router } from 'express';
import AdImageController from '../controllers/image';
import { adImageCheck } from '../middleware/validations/image';
import Auth from '../middleware/utils/Auth';

const { createAdImage, getAnImage, getAnAdImages, getAllImages
 } = AdImageController;
const { getUser } = Auth;

const adImageRouter = Router();

adImageRouter.post('/:adId/create', getUser, adImageCheck, createAdImage);
adImageRouter.get('/getAnImage/:imageId', getUser, getAnImage);
adImageRouter.get('/:adId/getAnAdImages', getUser, getAnAdImages);
adImageRouter.get('/getAllImages', getUser, getAllImages);

export default adImageRouter;