import React from 'react';

const FileItem = ({ name, level, isDirectory }) => {
  const handleDelete = (e) => {
    e.stopPropagation();
    const itemType = isDirectory ? "文件夹" : "文件";
    
    // 使用更友好的确认对话框
    if (window.confirm(`确定要删除${itemType} "${name}" 吗？\n此操作无法撤销。`)) {
      // TODO: 实现实际的删除逻辑
      console.log(`删除${itemType}: ${name}`);
    }
  };

  return (
    <div 
      className="file-item" 
      style={{ paddingLeft: `${level * 20}px` }}
    >
      <div className="file-content">
        <span className="file-icon">
          {isDirectory ? "📁" : "📄"}
        </span>
        <span>{name}</span>
      </div>
      <button 
        className="delete-btn" 
        onClick={handleDelete}
        title={`删除${isDirectory ? '文件夹' : '文件'}`}
      >
        <svg width="12" height="12" viewBox="0 0 24 24" fill="currentColor">
          <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/>
        </svg>
        删除
      </button>
    </div>
  );
};

const FileTree = ({ files }) => {
  const renderFileTree = (items, level = 0) => {
    return items.map((item) => (
      <React.Fragment key={item.id}>
        <FileItem
          name={item.name}
          level={level}
          isDirectory={item.type === 'directory'}
        />
        {item.children && item.children.length > 0 && 
          renderFileTree(item.children, level + 1)}
      </React.Fragment>
    ));
  };

  return (
    <div className="file-tree">
      {renderFileTree(files)}
    </div>
  );
};

export default FileTree; 