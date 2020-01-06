import Helper from '../utils/Helper';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}

export  const adCheck = (req, res, next) => {
  let { title, description, type, price, isNegotiable } = req.body;

  if (title) title = title.capitalize().trim();
  if (description) description = description.capitalize().trim();
  if (type) type = type.capitalize().trim();
  if (location) location = location.capitalize().trim();
  if (price) price = price;
  if (isNegotiable) isNegotiable = isNegotiable;

  const errors = [];
  let isEmpty;
  
  isEmpty = Helper.checkFieldEmpty(title, 'title');
  if (isEmpty) errors.push(isEmpty);

  isEmpty = Helper.checkFieldEmpty(description, 'description');
  if (isEmpty) errors.push(isEmpty);

  isEmpty = Helper.checkFieldEmpty(price, 'price');
  if (isEmpty) errors.push(isEmpty);

  if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

  req.body.title = title;
  req.body.description = description;
  req.body.price = price;
  req.body.type = type;
  req.body.isNegotiable = isNegotiable;
  req.body.location = location;
  return next();
}