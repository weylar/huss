import Helper from '../utils/Helper';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}

export const adReportCheck = (req, res, next) => {
  let { reason } = req.body;

  if (reason) reason = reason.capitalize().trim();

  const errors = [];
  let isEmpty;
  
  isEmpty = Helper.checkFieldEmpty(reason, 'reason');
  if (isEmpty) errors.push(isEmpty);

  if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

  req.body.reason = reason;
  return next();
}