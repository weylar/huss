import { Router } from 'express';
import AdController from '../controllers/product';
import { adCheck } from '../middleware/validations/product';
import Auth from '../middleware/utils/Auth';

const { createAd, getAd, getOwnAd, getAllAds
 } = AdController;
const { getUser, adminCheck } = Auth;

const adRouter = Router();

adRouter.post('/:categoryId/:subCategoryId/create', getUser, adCheck, createAd);
adRouter.get('/getAd/:adId', getUser, getAd);
adRouter.get('/getOwnAd/:adId', getUser, getOwnAd);
adRouter.get('/getAllAds', getUser, getAllAds);

export default adRouter;