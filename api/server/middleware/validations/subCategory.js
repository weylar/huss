import Helper from '../utils/Helper';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}

export  const subCategoryCheck = (req, res, next) => {
  let { name } = req.body;

  if (name) name = name.capitalize().trim();

  const errors = [];
  let isEmpty;
  
  isEmpty = Helper.checkFieldEmpty(name, 'name');
  if (isEmpty) errors.push(isEmpty);

  if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

  req.body.name = name;
  return next();
}