import { Router } from 'express';
import AdController from '../controllers/product';
import { adCheck } from '../middleware/validations/product';
import Auth from '../middleware/utils/Auth';

const { createAd, getAd, getOwnAd, getAllAds, getAllOwnAds, getAllAdsByLimit, getAllOwnAdsByLimit, paginateAds, paginateOwnAds,
  getAdsSuggest, getOwnAdsSuggest, getAdsByStatus, getOwnAdsByStatus, getOwnAdsByStatusSuggest, getAdsByStatusSuggest,
  makeAdInactive, makePayment, deactivatePayment, editAd, deleteAd, activateStatus
 } = AdController;
const { getUser } = Auth;

const adRouter = Router();

adRouter.post('/:categoryName/:subCategoryName/create', getUser, adCheck, createAd);
adRouter.get('/getAd/:adId', getUser, getAd);
adRouter.get('/getOwnAd/:adId', getUser, getOwnAd);
adRouter.get('/getAllAds', getUser, getAllAds);
adRouter.get('/getAllOwnAds', getUser, getAllOwnAds);
adRouter.get('/getAllAdsByLimit', getUser, getAllAdsByLimit);
adRouter.get('/getAllOwnAdsByLimit/:limit', getUser, getAllOwnAdsByLimit);
adRouter.get('/paginateAds/:offset/:limit', getUser, paginateAds);
adRouter.get('/paginateOwnAds/:offset/:limit', getUser, paginateOwnAds);
adRouter.get('/getAdsSuggest/:offset/:limit/:title', getUser, getAdsSuggest);
adRouter.get('/getOwnAdsSuggest/:offset/:limit/:title', getUser, getOwnAdsSuggest);
adRouter.get('/getAdsByStatus/:offset/:limit/:status', getUser, getAdsByStatus);
adRouter.get('/getOwnAdsByStatus/:offset/:limit/:status', getUser, getOwnAdsByStatus);
adRouter.get('/getOwnAdsByStatusSuggest/:offset/:limit/:status/:title', getUser, getOwnAdsByStatusSuggest);
adRouter.get('/getAdsByStatusSuggest/:offset/:limit/:status/:title', getUser, getAdsByStatusSuggest);
adRouter.put('/activateStatus/:adId', getUser, activateStatus);
adRouter.put('/makePayment/:adId', getUser, makePayment);
adRouter.put('/deactivatePayment/:adId', getUser, deactivatePayment);
adRouter.put('/editAd/:adId', getUser, editAd);
adRouter.delete('/deleteAd/:adId', getUser, deleteAd);

export default adRouter;