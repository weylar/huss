import Helper from '../utils/Helper';

export  const adImageCheck = (req, res, next) => {
  let { imageUrl, displayImage } = req.body;

  if (imageUrl) imageUrl = imageUrl
  if (displayImage) displayImage = displayImage

  const errors = [];
  let isEmpty;
  
  isEmpty = Helper.checkFieldEmpty(imageUrl, 'imageUrl');
  if (isEmpty) errors.push(isEmpty);

  if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

  req.body.imageUrl = imageUrl;
  req.body.displayImage = displayImage;
  return next();
}