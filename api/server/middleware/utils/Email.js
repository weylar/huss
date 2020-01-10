import nodemailer from 'nodemailer';

export const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: process.env.EMAIL_LOGIN,
    pass: process.env.EMAIL_PASSWORD
  }
});

export const getPasswordResetURL = (user, token) =>
  `http://localhost:3000/password/reset/${user.id}/${token}`;

export const resetPasswordTemplate = (user, url) => {
  const from = process.env.EMAIL_LOGIN;
  const to = user.email;
  const subject = '🌻 Huss.ng Password Reset 🌻';
  const html = `
  <p>Hey ${user.firstName || user.email},</p>
  <p>We heard that you lost your Huss.ng password. Sorry about that!</p>
  <p>But don’t worry! You can use the following link to reset your password:</p>
  <a href=${url}>${url}</a>
  <p>If you don’t use this link within 1 hour, it will expire.</p>
  <p>Do something outside today! </p>
  <p>–Your friends at Huss.ng</p>
  `;

  return { from, to, subject, html };
};
