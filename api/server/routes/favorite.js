import { Router } from 'express';
import FavoriteController from '../controllers/favorite';
import Auth from '../middleware/utils/Auth';

const { createFavorite, getAFavorite, getAllFavorites, getAllAdFavorites, getAllFavoritesByLimit, paginateAllFavorites, deleteFavorite } = FavoriteController;
const { getUser } = Auth;

const favoriteRouter = Router();

favoriteRouter.post('/:adId/create', getUser, createFavorite);
favoriteRouter.get('/:adId', getUser, getAFavorite);
favoriteRouter.get('/', getUser, getAllFavorites);
favoriteRouter.get('/getAllFavoritesByLimit/:limit', getUser, getAllFavoritesByLimit);
favoriteRouter.get('/paginateAllFavorites/:offset/:limit', getUser, paginateAllFavorites);
favoriteRouter.get('/:adId/adFavorites', getUser, getAllAdFavorites);
favoriteRouter.get('/:adId/deleteFavorite', getUser, deleteFavorite);

export default favoriteRouter;
