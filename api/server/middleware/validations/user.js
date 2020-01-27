import db from '../../src/models';
import Helper from '../utils/Helper';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}
class UserValidation {
  static async signUpCheck(req, res, next) {
    let {
      email, firstName, lastName, password, confirmPassword
    } = req.body;

    const errors = UserValidation.inputCheck(email, firstName, lastName, password, confirmPassword);
    if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

    if (email) email = email.toLowerCase().trim();
    if (firstName) firstName = firstName.capitalize().trim();
    if (lastName) lastName = lastName.capitalize().trim();
    if (password) password = password;
    if (confirmPassword) confirmPassword = confirmPassword;

    const passwordPattern = /\w{6,}/g;

    if (!passwordPattern.test(password)) {
      return res.status(406).json({
        status: 'error',
        statusCode: 406,
        error: 'Invalid password provided',
        message: 'Password must not be less than six(6) characters'
      });
    }

    if (password !== confirmPassword) {
      return res.status(422).json({
        status: 'error',
        statusCode: 422,
        error: 'Invalid password provided',
        message: 'Passwords do not match'
      });
    }

    const isInvalid = Helper.validateEmail(email);
    if (isInvalid) return res.status(isInvalid.statusCode).json(isInvalid);

    const result = await db.User.findOne({
      where: { email: req.body.email }
    });

    if (result) {
      return res.status(409).json({
        status: 'error',
        statusCode: 409,
        error: 'Email already in use',
        message: 'Please provide another email address',
      });
    }

    req.body.email = email;
    req.body.firstName = firstName;
    req.body.lastName = lastName;
    req.body.password = password;

    return next();
  }

  static inputCheck(email, firstName, lastName, password, confirmPassword) {
    const errors = [];
    let isEmpty;
    let hasWhiteSpace;
    let hasAlpha;

    isEmpty = Helper.checkFieldEmpty(email, 'email');
    if (isEmpty) errors.push(isEmpty);

    hasWhiteSpace = Helper.checkFieldWhiteSpace(email, 'email');
    if (hasWhiteSpace) errors.push(hasWhiteSpace);

    isEmpty = Helper.checkFieldEmpty(firstName, 'firstName');
    if (isEmpty) errors.push(isEmpty);

    hasWhiteSpace = Helper.checkFieldWhiteSpace(firstName, 'firstName');
    if (hasWhiteSpace) errors.push(hasWhiteSpace);

    hasAlpha = Helper.checkFieldAlpha(firstName, 'firstName');
    if (hasAlpha) errors.push(hasAlpha);

    isEmpty = Helper.checkFieldEmpty(lastName, 'lastName');
    if (isEmpty) errors.push(isEmpty);

    hasWhiteSpace = Helper.checkFieldWhiteSpace(lastName, 'lastName');
    if (hasWhiteSpace) errors.push(hasWhiteSpace);

    hasAlpha = Helper.checkFieldAlpha(lastName, 'lastName');
    if (hasAlpha) errors.push(hasAlpha);

    isEmpty = Helper.checkFieldEmpty(password, 'password');
    if (isEmpty) errors.push(isEmpty);

    isEmpty = Helper.checkFieldEmpty(confirmPassword, 'confirmPassword');
    if (isEmpty) errors.push(isEmpty);

    return errors;
  }

  static loginCheck(req, res, next) {
    let { email, password } = req.body;

    if (email) email = email.toLowerCase().trim();
    if (password) password = password;

    const errors = [];

    let isEmpty;
    isEmpty = Helper.checkFieldEmpty(email, 'email');
    if (isEmpty) errors.push(isEmpty);

    isEmpty = Helper.checkFieldEmpty(password, 'password');
    if (isEmpty) errors.push(isEmpty);

    if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

    req.body.email = email;
    req.body.password = password;
    return next();
  }

  static passwordCheck(req, res, next) {
    let { oldPassword, newPassword, confirmPassword } = req.body;

    if (oldPassword) oldPassword = oldPassword;
    if (newPassword) newPassword = newPassword;
    if (confirmPassword) confirmPassword = confirmPassword;

    const errors = [];

    let isEmpty;
    isEmpty = Helper.checkFieldEmpty(oldPassword, 'oldPassword');
    if (isEmpty) errors.push(isEmpty);

    isEmpty = Helper.checkFieldEmpty(newPassword, 'newPassword');
    if (isEmpty) errors.push(isEmpty);

    isEmpty = Helper.checkFieldEmpty(confirmPassword, 'confirmPassword');
    if (isEmpty) errors.push(isEmpty);

    if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

    req.body.password = newPassword;
    return next();
  }

  static userDetailsCheck(req, res, next){
    let { state, city, phoneNumber, profileImgUrl, firstName, lastName } = req.body;

    if (state) state = state.capitalize().trim();
    if (city) city = city.capitalize().trim();
    if (phoneNumber) phoneNumber = phoneNumber.trim();
    if (profileImgUrl) profileImgUrl = profileImgUrl;
    if (firstName) firstName = firstName;
    if (lastName) lastName = lastName;

    req.body.state = state;
    req.body.city = city;
    req.body.phoneNumber = phoneNumber;
    req.body.profileImgUrl = profileImgUrl;
    req.body.firstName = firstName;
    req.body.lastName = lastName;
    return next();
  }

  static userImageCheck(req, res, next){
    const { profileImgUrl } = req.body;

    const errors = [];

    let isEmpty;

    isEmpty = Helper.checkFieldEmpty(profileImgUrl, 'profileImgUrl');
    if (isEmpty) errors.push(isEmpty);

    if (errors.length > 0) return res.status(errors[0].statusCode).json(errors[0]);

    req.body.profileImgUrl = profileImgUrl;
    return next();
  }
}

export default UserValidation;