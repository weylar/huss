import { Router } from 'express';
import FavoriteController from '../controllers/favorite';
import Auth from '../middleware/utils/Auth';

const { createFavorite, getAFavorite, getAllFavorites, getAllAdFavorites } = FavoriteController;
const { getUser } = Auth;

const favoriteRouter = Router();

favoriteRouter.post('/:adId/create', getUser, createFavorite);
favoriteRouter.get('/:id', getUser, getAFavorite);
favoriteRouter.get('/', getUser, getAllFavorites);
favoriteRouter.get('/:adId/adFavorites', getUser, getAllAdFavorites);

export default favoriteRouter;
