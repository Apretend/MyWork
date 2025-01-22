import React, { useState } from 'react';
import '../styles/Login.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('提交登录表单');
  };

  // 添加测试内容
  console.log('Login component rendered');

  return (
    <>
      {/* 添加测试内容 */}
      <h1 style={{color: 'red'}}>测试登录页面</h1>
      
      <div className="login-container">
        <div className="login-box">
          <div className="login-header">
            <svg className="apple-logo" viewBox="0 0 24 24" width="35" height="35">
              <path fill="currentColor" d="M17.05 20.28c-.98.95-2.05.88-3.08.4-1.09-.5-2.08-.53-3.24 0-1.44.66-2.2.53-3.08-.4C4.4 16.84 4.06 12 6.53 9.33c1.53-1.66 3.1-1.34 4.36-.81 1.2.5 1.86.5 3.06 0 1.32-.55 2.8-.93 4.16.53-.11.07-2.47 1.44-2.44 4.28.03 3.37 2.94 4.45 2.97 4.47-.03.07-.45 1.55-1.5 3.07l-.09.11z"/>
              <path fill="currentColor" d="M15.12 6.45c.74-.9 1.24-2.15 1.1-3.45-1.07.04-2.36.71-3.13 1.61-.69.8-1.29 2.08-1.12 3.3 1.19.09 2.41-.6 3.15-1.46z"/>
            </svg>
            <h1>登录 Apple ID</h1>
          </div>

          <form onSubmit={handleSubmit} className="login-form">
            <div className="input-group">
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Apple ID"
                required
              />
            </div>

            <div className="input-group">
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="密码"
                required
              />
            </div>

            <button type="submit" className="login-button">
              继续
            </button>

            <div className="login-footer">
              <a href="#forgot" className="forgot-link">忘记 Apple ID 或密码？</a>
              <div className="divider">或</div>
              <a href="#create" className="create-link">创建 Apple ID</a>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default Login; 