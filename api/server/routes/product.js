import { Router } from 'express';
import AdController from '../controllers/product';
import { adCheck } from '../middleware/validations/product';
import Auth from '../middleware/utils/Auth';

const { createAd, getAd, getOwnAd, getAllAds, getAllOwnAds, getAllAdsByLimit, getAllOwnAdsByLimit, paginateAds
 } = AdController;
const { getUser, adminCheck } = Auth;

const adRouter = Router();

adRouter.post('/:categoryId/:subCategoryId/create', getUser, adCheck, createAd);
adRouter.get('/getAd/:adId', getUser, getAd);
adRouter.get('/getOwnAd/:adId', getUser, getOwnAd);
adRouter.get('/getAllAds', getUser, getAllAds);
adRouter.get('/getAllOwnAds', getUser, getAllOwnAds);
adRouter.get('/getAllAdsByLimit/:limit', getUser, getAllAdsByLimit);
adRouter.get('/getAllOwnAdsByLimit/:limit', getUser, getAllOwnAdsByLimit);
adRouter.get('/paginateAds/:offset/:limit', getUser, paginateAds);

export default adRouter;