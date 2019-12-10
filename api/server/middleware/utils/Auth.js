import jwt from 'jsonwebtoken';
import dotenv from 'dotenv';

dotenv.config();

class Auth {
  static getUser(req, res, next) {
    try {
      if (!req.headers.authorization) throw new Error('No token provided, You do not have access to this page');
      const token = req.headers.authorization.split(' ')[1];
      const decoded = jwt.verify(token, process.env.SECRET);

      req.userId = decoded.id;
      req.userEmail = decoded.email

      return next();
    } catch (e) {
      return res.status(401).send({
        status: 'error',
        statusCode: 401,
        error: `${e.name}. ${e.message}`,
      });
    }
  }
}

export default Auth;