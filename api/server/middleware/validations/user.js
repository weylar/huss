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
    if (errors.length > 0) return res.status(errors[0].statusCode).send(errors[0]);

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
    if (isInvalid) return res.status(isInvalid.statusCode).send(isInvalid);

    const result = await db.User.findOne({
      where: { email: req.body.email }
    });

    if (result) {
      return res.status(409).send({
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
    let hasNumber

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

    if (errors.length > 0) return res.status(errors[0].statusCode).send(errors[0]);

    req.body.email = email;
    req.body.password = password;
    return next();
  }

  static userDetailsCheck(req, res, next){
    let { state, city, phoneNumber } = req.body;

    if (state) state = state.capitalize().trim();
    if (city) city = city.capitalize().trim();
    if (phoneNumber) phoneNumber = phoneNumber.trim();

    const errors = [];

    let isEmpty;
    let hasWhiteSpace;
    let hasAlpha;
    let hasNumber;

    isEmpty = Helper.checkFieldEmpty(state, 'state');
    if (isEmpty) errors.push(isEmpty);

    hasWhiteSpace = Helper.checkFieldWhiteSpace(state, 'state');
    if (hasWhiteSpace) errors.push(hasWhiteSpace);

    hasAlpha = Helper.checkFieldAlpha(state, 'state');
    if (hasAlpha) errors.push(hasAlpha);

    isEmpty = Helper.checkFieldEmpty(city, 'city');
    if (isEmpty) errors.push(isEmpty);

    hasWhiteSpace = Helper.checkFieldWhiteSpace(city, 'city');
    if (hasWhiteSpace) errors.push(hasWhiteSpace);

    hasAlpha = Helper.checkFieldAlpha(city, 'city');
    if (hasAlpha) errors.push(hasAlpha);

    isEmpty = Helper.checkFieldEmpty(phoneNumber, 'phoneNumber');
    if (isEmpty) errors.push(isEmpty);

    hasNumber = Helper.checkFieldNumber(phoneNumber, 'phoneNumber');
    if (hasNumber) errors.push(hasNumber);

    if (errors.length > 0) return res.status(errors[0].statusCode).send(errors[0]);

    req.body.state = state;
    req.body.city = city;
    req.body.phoneNumber = phoneNumber;
    return next();
  }

  static userImageCheck(req, res, next){
    const { profileImgUrl } = req.body;

    const errors = [];

    let isEmpty;

    isEmpty = Helper.checkFieldEmpty(profileImgUrl, 'profileImgUrl');
    if (isEmpty) errors.push(isEmpty);

    if (errors.length > 0) return res.status(errors[0].statusCode).send(errors[0]);

    req.body.profileImgUrl = profileImgUrl;
    return next();
  }
}

export default UserValidation;