<template>
  <div class="story-management-tools">
    <div class="file-management">
      <el-autocomplete
      v-model="state"
      :fetch-suggestions="querySearchAsync"
      placeholder="请输入书名"
      @select="handleSelect"
      clearable
      ></el-autocomplete>
    </div>
    <div class="file-tree">
      <div class="book-title">
        <div class="book-name">{{ book.name }}</div>
        <div class="book-button">
          <el-button v-if="book.name" type="primary" link @click="reNameBook()">重命名</el-button>
          <el-button v-if="book.name" type="primary" link @click="appendDirectory()">添加文件夹</el-button>
        </div>
      </div>
      <div class="book-file">
        <el-tree
        :data="treeData"
        :props="defaultProps"
        @node-click="handleNodeClick"
        show-checkbox
        node-key="id"
        :expand-on-click-node="true"
        class="el-tree-node"
        >
          <template #default="{ node, data }">
            <div class="custom-tree-node" @click="handleNodeClickArea(data)">
              <span style="width:150px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis" class="custom-tree-node-label">{{ data.type === 'directory' ? node.label : node.label + (data.title === null ? "" : "：" + data.title)}}</span>
              <div style="flex:1">
                <el-button type="primary" link @click.stop="reName(data)">重命名</el-button>
                <el-button  v-if="data.type == 'directory'" type="primary" link @click.stop="append(data)">添加</el-button>
                <el-button  type="danger" link @click.stop="remove(node, data)">删除</el-button>
              </div>
            </div>
          </template>
        </el-tree>
      </div>
    </div>
  </div>
  <el-dialog v-model="dialogVisible" title="添加节点">
    <div style="display: flex;flex-direction: row;">
      <div class="custom-tree-node-select" style="margin-right: 10px;">
      <el-select
        v-model="createFileType"
        placeholder="文件类型"
        style="width: 100px"
      >
      <el-option
        v-for="item in createFileTypeOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    </div>
    <div class="custom-tree-node-input" v-if="createFileType == 1" style="width: 100%;">
      <el-input v-model="newNodeLabel" placeholder="请输入内容"></el-input>
    </div>
    <div class="custom-tree-node-input" v-else>
      <el-input v-model="newNodeLabel" style="max-width: 200px;" type="number">
        <template #prepend>第</template>
        <template #append>章</template></el-input>
    </div>
    </div>
    <template #footer>
      <el-button type="danger" @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmAppend(createFileType)">确定</el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogDirectoryVisible" title="添加目录">
    <el-input v-model="newDirectory" placeholder="请输入内容"></el-input>
    <template #footer>
      <el-button type="danger" @click="dialogDirectoryVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmDirectoryAppend">确定</el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogFileReNameVisible" title="重命名">
    <el-input v-model="newFileName" placeholder="请输入内容"></el-input>
    <template #footer>
      <el-button type="danger" @click="dialogFileReNameVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmReName">确定</el-button>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogReNameBookVisible" title="书名重命名">
    <el-input v-model="newBookName" placeholder="请输入内容"></el-input>
    <template #footer>
      <el-button type="danger" @click="dialogReNameBookVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmReNameBook">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';
import { ca, el, tr } from 'element-plus/es/locale';
import { Interface } from 'readline';
import { json } from 'stream/consumers';
import { ref, onMounted, reactive } from 'vue';
import { defineEmits, watch } from 'vue';

const emit = defineEmits<{
  (e: 'itemClicked', id: number): void;
}>();

const props = defineProps(['updateDocumentSuccessed']);
watch(() => props.updateDocumentSuccessed, () => {
  getTreeData();
})
const handleNodeClickArea = (data) => {
  if (data.type == "file") {
    sendItemId(data.id);  
  }
}
const sendItemId = (id: number) => {
  console.log(id);
  
  emit('itemClicked', id);
}


onMounted(() => {
  console.log("storyManagementTools mounted");
  getAllBookInfo();
})

const userId = ref(localStorage.getItem("userId"));

const createFileType = ref("2");
const createFileTypeOptions = [
  {
    value: '1',
    label: '文件'
  },
  {
    value: '2',
    label: '正文'
  }
]

const storys = ref([{}]);   // 获取的只是名字和ID
const state = ref("");   // 搜索框的参数
const timeout = ref(null);
const book = reactive({
  id: 0,
  name: "",
})
const dialogVisible = ref(false);
const dialogFileReNameVisible = ref(false);
const dialogDirectoryVisible = ref(false);
const dialogReNameBookVisible = ref(false);
const newNodeLabel = ref("");
const newFileName = ref("");
const newBookName = ref("");
const targetNode = ref(null);
const newDirectory = ref(null);

interface Tree {
  id: number;
  label: string;
  children?: Tree[];
}


const treeData = ref([])
// const treeData = ref([
//   {
//     name:"src",
//     id:1,
//     children: [
//       { name:"index.html", id:2, children: [
//         {name: "index.js", id:3 }
//       ] },
//       { name:"index.js", id:4 },
//       { name:"index.css", id:5 },
//     ]
//   }
// ])
const defaultProps = reactive({
  children: 'children',
  label: 'name'
})

const getAllBookInfo = async () => {
  try {
    const response = await axios.get(`/api/books/getBooks`);
    if (response.data.code === 0) {
      storys.value = response.data.data;
    }
  } catch (error) {
    console.log("获取失败：", error);
  }
}

/**
 * 异步查询搜索建议
 * 
 * @param {string} queryString - 用户输入的查询字符串
 * @param {function} cb - 回调函数，用于处理搜索结果
 */
const querySearchAsync = async (queryString, cb) => {
  // 当查询字符串为空时，直接返回空数组
  if (!queryString || queryString == ""){
    cb([]);
    return;
  }
  
  // 清除上一次的定时器，以避免重复请求
  clearTimeout(timeout.value);
  
  // 设置一个新的定时器，延迟执行搜索请求
  timeout.value = setTimeout(async () => {
    try{
      // 执行实际的搜索请求（已注释的代码示例）
      const response = await axios.post(`/api/books/getBookByName`, { bookName: queryString , authorId: localStorage.getItem('userId')});
      const data = response.data.data.map(({bookId, bookName}) => ({id: bookId, name: bookName}))
      const result = data.map((item) => ({ value: item.name, id: item.id }));
      // 调用回调函数处理搜索结果
      cb(result);
    } catch (error) {
      // 请求失败时，打印错误信息并返回空数组
      console.log("请求失败：", error);
      cb([]);
    }
  },300)
}
/**
 * 创建一个状态过滤函数
 * 
 * 该函数用于生成一个过滤函数，该过滤函数可以筛选出在给定查询字符串中匹配的状态对象
 * 主要用于在大量状态数据中快速找到匹配项
 * 
 * @param {string} queryString - 查询字符串，用于匹配状态对象的值
 * @returns {Function} 返回一个过滤函数，该函数接受一个状态对象，并返回一个布尔值，表示该状态是否匹配查询字符串
 */
const createStateFilter = (queryString) => {
  return (state) => {
    // 检查状态对象的值是否包含查询字符串，不区分大小写
    return (state.value.toLowerCase().includes(queryString.toLowerCase()));
  }
}
/**
 * 处理选择事件的函数
 * 当用户选择某一选项时，该函数会被触发
 * 它的主要作用是将所选选项的ID和值赋给book对象
 * 这样做是为了在界面上显示或进一步处理用户的选择
 * 
 * @param {Object} item - 被选中的选项对象，包含id和value属性
 */
const handleSelect = async (item) => {
  // 将选中项的ID赋给book对象的id属性
  book.id = item.id;
  // 将选中项的值赋给book对象的name属性
  book.name = item.value;
  state.value = "";
  getTreeData();
}

const handleNodeClick = async (data) => {
  console.log(data);
}

const getTreeData = async () => {
  try {
    const response = await axios.get(`/api/fileManagementTools/getFileByBookId/${book.id}`);
    if (response.data.code === 0) {
      treeData.value = response.data.data;
    }
  } catch (error) {
    console.log("获取失败：", error);
  }
}

const append = (data) => {
  targetNode.value = ""
  newNodeLabel.value = "";
  targetNode.value = data;
  dialogVisible.value = true;
}
const confirmAppend = async (createFileType) => {
  if (newNodeLabel.value == ""){
    ElMessage.warning("请输入内容");
    return;
  }
  try {
    const nodeData = {
      directoryId: targetNode.value.id,
      documentName: createFileType.value === '1' ? newNodeLabel.value : "第" + newNodeLabel.value + "章：",
      bookId: book.id,
      documentContent: "",
      documentTitle: "",
    }
    const response = await axios.post(`/api/fileManagementTools/addDoucment`, nodeData)
    if (response.data.code === 0) {
      getTreeData();
      ElMessage.success("添加成功");
      dialogVisible.value = false;
    }else{
      throw new Error("请求失败" + response.status);
    }
  } catch (error) {
    ElMessage.error("添加失败");
    console.log("请求失败：", error);
  }
  
}

const appendDirectory = () => {
  newDirectory.value = "";
  dialogDirectoryVisible.value = true;
}
const confirmDirectoryAppend = async () => {
  try {
    if (newDirectory.value == ""){
      ElMessage.warning("请输入内容");
      return;
    }
    const nodeData = {
      bookId: book.id,
      directoryName: newDirectory.value,
    }
    console.log(nodeData);
    
    const response = await axios.post(`/api/fileManagementTools/addDirectory`, nodeData);
    if (response.data.code === 0) {
      getTreeData();
      ElMessage.success("添加成功");
      dialogDirectoryVisible.value = false;
    } else {
      ElMessage.error("添加失败");
    }
  } catch (error) {
    console.log("请求失败：", error);
  }
}

const reName = (data) => {
  targetNode.value = "";
  newFileName.value = "";
  newFileName.value = data.name;
  targetNode.value = data;
  dialogFileReNameVisible.value = true;
}

const confirmReName = async () => {
  try {
    const isFile = targetNode.value.type === "file";
    const nodeData = {
      bookId: book.id,
      [isFile ? "documentId" : "directoryId"]: targetNode.value.id,
      [isFile ? "documentName" : "directoryName"]: newFileName.value,
    }
    const response = await axios.post(`/api/fileManagementTools/updateFile`,nodeData)
    if (response.data.code === 0) {
      getTreeData();
      ElMessage.success("修改成功");
      dialogFileReNameVisible.value = false;
    } else {
      ElMessage.error("修改失败");
    }
  } catch (error) {
    console.log("请求失败：", error);
  }
}

const reNameBook = () => {
  newBookName.value = book.name;
  dialogReNameBookVisible.value = true;
}
const confirmReNameBook = async () => {
  try {
    if (newBookName.value == ""){
      ElMessage.warning("请输入内容");
      return;
    }
    const nodeData = {
      bookId: book.id,
      bookName: newBookName.value,
    }
    const response = await axios.post(`/api/fileManagementTools/updateBook`,nodeData);
    if (response.data.code === 0) {
      getTreeData();
      ElMessage.success("修改成功");
      book.name = newBookName.value;
      dialogReNameBookVisible.value = false;
    } else {
      ElMessage.error("修改失败");
    }
  } catch (error) {
    console.log("请求失败：", error);
  }
}

const remove = async (data) => {
  try {
    ElMessageBox.confirm('确定删除吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(async () => {
      const isFile = data.data.type === "file";
      const nodeData = {
        bookId: book.id,
        [isFile ? "documentId" : "directoryId"]: data.data.id,
      }
      const response = await axios.post(`/api/fileManagementTools/deleteFile`,nodeData)
      if (response.data.code === 0) {
        getTreeData();
        ElMessage.success("删除成功");
      } else {
        ElMessage.error("删除失败");
      }
    })
  } catch (error) {
    console.log("请求失败：", error);
  }
}


</script>

<style scoped lang="scss">
.story-management-tools {
    display: flex;
    flex-direction: column;
    width: 400px;
    min-height: 700px;
    padding: 10px 25px;

    .file-management {
      margin-bottom: 16px;
    }

    .file-tree {
      flex: 1;
      width: 100%;
      overflow-x: scroll;
      overflow-y: scroll;
      display: flex;
      flex-direction: column;
      // border: 1px solid purple;
      
      .book-title {
        height: 35px;
        border-bottom: 1px solid #E0E0E0;
        padding: 0px 8px 0px 8px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        white-space: nowrap;
        background-color: white;
        


        .book-name {
          font-size: 20px;
          font-weight: 700;
          line-height: 35px;
          max-width: 350px;
          overflow-x: scroll;
          white-space: nowrap;
        }

      }

      .book-file {
        flex: 1;

        .el-tree-node {
          height: 100%;
        }
      }

      .custom-tree-node {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        padding-right: 8px;
      }
    }

}

</style>