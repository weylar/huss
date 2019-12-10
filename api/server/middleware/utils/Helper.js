import dotenv from 'dotenv';
import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';

dotenv.config();

class Helper {
  static getToken(userPayload) {
    return jwt.sign(userPayload, process.env.SECRET, { expiresIn: '365d' });
  }

  static hashPassword(password) {
    const hash = bcrypt.hashSync(password, 10);
    return hash;
  }

  static comparePassword(password, hash) {
    const result = bcrypt.compareSync(password, hash);
    return result;
  }

  static checkFieldEmpty(value, field) {
    if (!value) {
      return {
        status: 'error',
        statusCode: 422,
        error: `Invalid ${field} provided`,
        message: `${field} cannot be empty`,
      };
    }
    return false;
  }

  static checkFieldWhiteSpace(value, field) {
    if (/\s/.test(value)) {
      return {
        status: 'error',
        statusCode: 422,
        error: `Invalid ${field} provided`,
        message: `No whitespaces allowed in ${field}`,
      };
    }
    return false;
  }

  static checkFieldAlpha(value, field) {
    const pattern = /^[a-zA-Z]+$/;
    if (!pattern.test(value)) {
      return {
        status: 'error',
        statusCode: 422,
        error: `Invalid ${field} provided`,
        message: `${field} must contain only alphabets`,
      };
    }
    return false;
  }

  static checkFieldNumber(value, field) {
    if (!Number(value)) {
      return {
        status: 'error',
        statusCode: 422,
        error: `Invalid ${field} provided`,
        message: `${field} must contain only numbers`,
      };
    }
    return false;
  }

  static validateEmail(email) {
    // eslint-disable-next-line no-useless-escape
    const emailPattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (!emailPattern.test(email)) {
      return {
        status: 'error',
        statusCode: 422,
        error: 'Invalid email address',
        message: 'Please provide a valid email address',
      };
    }
    return false;
  }
}

export default Helper;