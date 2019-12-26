import { Router } from 'express';
import AdImageController from '../controllers/image';
import { adImageCheck } from '../middleware/validations/image';
import Auth from '../middleware/utils/Auth';

const { createAdImage, getAnImage, getAnAdImages, getAllImages, deleteImage
 } = AdImageController;
const { getUser } = Auth;

const adImageRouter = Router();

adImageRouter.post('/:adId/create', getUser, adImageCheck, createAdImage);
adImageRouter.get('/getAnImage/:imageId', getUser, getAnImage);
adImageRouter.get('/:adId/getAnAdImages', getUser, getAnAdImages);
adImageRouter.get('/getAllImages', getUser, getAllImages);
adImageRouter.delete('/:imageId/deleteImage', getUser, deleteImage);

export default adImageRouter;