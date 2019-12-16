import chai from 'chai';

import chaiHttp from 'chai-http';

import app from '../index';

const { expect } = chai;

chai.use(chaiHttp);

describe('POST api/v1/auth/signup', () => {
  it('It should successfully sign up a user and return a token', done => {
    const user = {
      email: 'samuel.olabiyi@gmail.com',
      firstName: 'Samuel',
      lastName: 'Olabiyi',
      password: 'samuelolabiyi',
      confirmPassword: 'samuelolabiyi'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(201);
        expect(res.body.status).to.be.equal('success');
        expect(res.body.statusCode).to.be.equal(201);
        expect(res.body.message).to.be.equal('You have successfully signed up on Huss.ng');
        expect(res.body.data).to.have.keys(
          'id',
          'email',
          'firstName',
          'lastName',
          'state',
          'city',
          'phoneNumber',
          'profileImageUrl',
          'token'
        );
        expect(res.body.data.token).to.be.a('string');
        expect(res.body.data.id).to.be.a('number');
        done();
      });
  });
  it('Should return an error if a user tries to sign up without a firstName', done => {
    const user = {
      firstName: '',
      lastName: 'bellion',
      email: 'jon@gmail.com',
      password: 'simpleandweet',
      confirmPassword: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid firstName provided');
        expect(res.body.message).to.be.equal('firstName cannot be empty');
        done();
      });
  });
  it('Should return an error if a user tries to sign up without a last_name', done => {
    const user = {
      firstName: 'jon',
      lastName: '',
      email: 'jon@gmail.com',
      password: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid lastName provided');
        expect(res.body.message).to.be.equal('lastName cannot be empty');
        done();
      });
  });
  it('Should return an error if a user tries to sign up without an email address', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: '',
      password: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid email provided');
        expect(res.body.message).to.be.equal('email cannot be empty');
        done();
      });
  });
  it('Should return an error if a user tries to sign up without a password', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: 'jon@gmail.com',
      password: ''
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid password provided');
        expect(res.body.message).to.be.equal('password cannot be empty');
        done();
      });
  });
  it('Should return an error if a user tries to sign up with a password less than 6 characters', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: 'jon@gmail.com',
      password: 'simpl',
      confirmPassword: 'simpl'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(406);
        expect(res.body.statusCode).to.be.equal(406);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid password provided');
        expect(res.body.message).to.be.equal('Password must not be less than six(6) characters');
        done();
      });
  });
  it('Should return an error if a user tries to sign up with a non-alpabetic first_name', done => {
    const user = {
      firstName: '..',
      lastName: 'bellion',
      email: 'jon@gmail.com',
      password: 'simpleandweet',
      confirmPassword: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid firstName provided');
        expect(res.body.message).to.be.equal('firstName must contain only alphabets');
        done();
      });
  });
  it('Should return an error if a user tries to sign up with a non-alpabetic lastName', done => {
    const user = {
      firstName: 'jon',
      lastName: '1234',
      email: 'jonny@gmail.com',
      password: 'simpleandweet',
      confirmPassword: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid lastName provided');
        expect(res.body.message).to.be.equal('lastName must contain only alphabets');
        done();
      });
  });

  it('Should return an error if a user tries to sign up with an invalid email address', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: 'jongmail.com',
      password: 'simpleandweet',
      confirmPassword: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid email address');
        expect(res.body.message).to.be.equal('Please provide a valid email address');
        done();
      });
  });
  it('Should return an error if a user tries to sign up without confirming password', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: 'jongmail.com',
      password: 'simpleandweet',
      confirmPassword: ''
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid confirmPassword provided');
        expect(res.body.message).to.be.equal('confirmPassword cannot be empty');
        done();
      });
  });
  it('Should return an error if a user tries to sign up without matching passwords', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: 'jongmail.com',
      password: 'simpleandweet',
      confirmPassword: 'sfdd'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(422);
        expect(res.body.statusCode).to.be.equal(422);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Invalid password provided');
        expect(res.body.message).to.be.equal('Passwords do not match');
        done();
      });
  });
  it('Should return an error if a user tries to sign up with a taken email address', done => {
    const user = {
      firstName: 'jon',
      lastName: 'bellion',
      email: 'samuel.olabiyi@gmail.com',
      password: 'simpleandweet',
      confirmPassword: 'simpleandweet'
    };
    chai
      .request(app)
      .post('/api/v1/auth/signup')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(409);
        expect(res.body.statusCode).to.be.equal(409);
        expect(res.body).to.have.keys('status', 'statusCode', 'error', 'message');
        expect(res.body.error).to.be.equal('Email already in use');
        expect(res.body.message).to.be.equal('Please provide another email address');
        done();
      });
  });
});

describe('POST api/v1/auth/login', () => {
  it('Should successfully sign in a user', done => {
    const user = {
      email: 'adio.usman@gmail.com',
      password: 'samuelolabiyi'
    };
    chai
      .request(app)
      .post('api/v1/auth/login')
      .send(user)
      .end((err, res) => {
        expect(res).to.have.status(200);
        expect(res.body.statusCode).to.be.equal(200);
        expect(res.body).to.have.keys('status', 'statusCode', 'data', 'message');
        expect(res.body.data).to.have.keys('userId', 'email', 'firstName', 'lastName', 'token');
        expect(res.body.data.token).to.be.a('string');
        expect(res.body.message).to.be.equal('Welecome, Samuel Olabiyi');
        done();
      });
  });
});
