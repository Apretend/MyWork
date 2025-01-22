import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Login from './components/Login';

function App() {
  return (
    <Router>
      <div>
        <nav>
          <Link to="/">首页</Link>
          <Link to="/about">关于</Link>
          <Link to="/login">登录</Link>
        </nav>

        <Routes>
          <Route path="/" element={<div>首页内容</div>} />
          <Route path="/about" element={<div>关于页面</div>} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App; 