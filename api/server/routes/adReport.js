import { Router } from 'express';
import AdReportController from '../controllers/adReport';
import { adReportCheck } from '../middleware/validations/adReport';
import Auth from '../middleware/utils/Auth';

const { createAdReport, getAdReport, getAllAdsReports, getReportsOfASingleAd, getReportsOfASingleUser,
deleteAnAdReport } = AdReportController;
const { getUser, adminCheck } = Auth;

const adReportRouter = Router();

adReportRouter.post('/create', getUser, adReportCheck, createAdReport);
adReportRouter.get('/getAdReport/:id', getUser, adminCheck, getAdReport);
adReportRouter.get('/getAllAdsReports', getUser, adminCheck, getAllAdsReports);
adReportRouter.get('/getReportsOfASingleAd/:adId', getUser, adminCheck, getReportsOfASingleAd);
adReportRouter.get('/getReportsOfASingleUser/:userId', getUser, adminCheck, getReportsOfASingleUser);
adReportRouter.delete('/deleteAnAdReport/:adId', getUser, adminCheck, deleteAnAdReport);

export default adReportRouter;