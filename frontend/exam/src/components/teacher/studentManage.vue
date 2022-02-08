<!--
描述：教师端，查看所有学生
作者：211027137 林羽希
完善：211027134 叶怀生
创建：unkown
修改：2022年2月9日17:28:31
-->
<!--
[WARNING]
存在一些细节问题，首先是年级、班级信息缺失。
其次是增加编辑学生时，学生的学院、专业不存在外键约束，可以随便填写。
-->
<template>
  <div class="all">
    <el-button type="primary" icon="el-icon-plus" @click="addStu()">添加学生</el-button>
    <div style="margin-bottom: 14px"></div>
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="sno" label="学号" width="160"></el-table-column>
      <el-table-column fixed="left" prop="sname" label="姓名" width="160"></el-table-column>
      <el-table-column prop="scollage" label="学院" width="240"></el-table-column>
      <el-table-column prop="smajor" label="专业" width="240"></el-table-column>
      <!-- [WARNING] 由于后端未提供这两个字段，导致留白。需要的话，可在后端提供固定内容，以正常显示。 -->
      <!--
      <el-table-column prop="grade" label="年级" width="160"></el-table-column>
      <el-table-column prop="cname" label="班级" width="240"></el-table-column>
      -->
      <el-table-column prop="sphone" label="联系方式" width="160"></el-table-column>
      <el-table-column prop="padding" label=" " width="360"></el-table-column>
      <el-table-column fixed="right" label="操作" width="220">
        <template slot-scope="scope">
          <el-button @click="editById(scope.row)" type="primary" size="small" icon="el-icon-edit">编辑</el-button>
          <el-button @click="submitDeleteById(scope.row.id)" type="danger" size="small" icon="el-icon-delete">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page">
    </el-pagination>
    <!-- 新增对话框-->
    <el-dialog
      title="添加学生信息"
      :visible.sync="addDialogVisible"
      width="500px"
      :before-close="handleClose">
      <section class="update">
        <el-descriptions title="" direction="vertical" :column="2" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              学号
            </template>
            <el-input size="small" v-model="addFormData.sno"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              姓名
            </template>
            <el-input size="small" v-model="addFormData.sname"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              学院
            </template>
            <el-input size="small" v-model="addFormData.scollage"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-wallet"></i>
              专业
            </template>
            <el-input size="small" v-model="addFormData.smajor"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              联系方式
            </template>
            <el-input size="small" v-model="addFormData.sphone"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-lock"></i>
              登录密码
            </template>
            <el-input size="small" v-model="addFormData.password" show-password></el-input>
          </el-descriptions-item>
        </el-descriptions>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAdd()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑学生信息"
      :visible.sync="editDialogVisible"
      width="500px"
      :before-close="handleClose">
      <section class="update">
        <el-descriptions title="" direction="vertical" :column="2" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              学号
            </template>
            {{editFormData.sno}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              姓名
            </template>
            <el-input size="small" v-model="editFormData.sname"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-office-building"></i>
              学院
            </template>
            <el-input size="small" v-model="editFormData.scollage"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-wallet"></i>
              专业
            </template>
            <el-input size="small" v-model="editFormData.smajor"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              联系方式
            </template>
            <el-input size="small" v-model="editFormData.sphone"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-lock"></i>
              登录密码
            </template>
            <el-input size="small" v-model="editFormData.password" show-password></el-input>
          </el-descriptions-item>
        </el-descriptions>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEdit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        records: [],
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      editDialogVisible: false, // 编辑对话框
      addDialogVisible: false,  // 添加对话框
      addFormData: {},  // 存储新增学生使用的数据
      editFormData: {}, // 存储编辑学生使用的数据
      webSite: 'http://8.130.16.20:8080/' // 站点地址
    };
  },
  created() {
    this.getStudentInfo();
  },
  methods: {
    //分页查询所有学生信息
    getStudentInfo() {
      let submitData = {
        pageNum: this.pagination.current,
        pageSize: this.pagination.size
      };
      this.$axios({
        url: this.webSite + 'ShowStudent',
        method: 'post',
        data: submitData
    }).then(res => {
      let resData = res.data.data.students;
      this.pagination.records = resData;
      }).catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getStudentInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getStudentInfo();
    },
    // 新增学生
    addStu(){
      this.addFormData = {
        sno: '',
        sname: '',
        sphone: '' ,
        scollage: '计算机与大数据学院',
        smajor: '电子信息',
        password: ''};
      this.addDialogVisible = true
    },
    submitAdd(){
      this.addDialogVisible = false;
      this.$axios({
        url: this.webSite + 'AddStudent',
        method: 'post',
        data: this.addFormData
      }).then(res => {
        if(res.data.code ==200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          })
        }else{
          this.$message({
            message: '添加失败，' + res.data,
            type: 'warning'
          })
        }
        this.getStudentInfo()
      });
    },
    // 修改学生信息
    editById(stuData) {
      this.editDialogVisible = true;
      // [WARNING] 采用深拷贝，可简化以下代码
      this.editFormData = {
        id: stuData.id,
        sno: stuData.sno,
        sname: stuData.sname,
        sphone: stuData.sphone,
        scollage: stuData.scollage,
        smajor: stuData.smajor,
        password: stuData.password};
    },
    submitEdit() { // 提交学生信息更改
      this.editDialogVisible = false
      this.$axios({
        url: this.webSite + 'UpdateStudent',
        method: 'post',
        data: this.editFormData
      }).then(res => {
        if(res.data.code ==200) {
          this.$message({
            message: '编辑成功',
            type: 'success'
          })
        }else{
          this.$message({
            message: '编辑失败，' + res.data,
            type: 'warning'
          })
        }
        this.getStudentInfo()
      });
    },
    // 删除当前学生
    submitDeleteById(studentId) {
      this.$confirm("确定删除当前学生吗？删除后无法恢复","Warning",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着吧',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: this.webSite + 'DeleteStudent',
          method: 'post',
          data: {sid: studentId}
        }).then(res => {
          if(res.data.code ==200) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
          }else{
            this.$message({
              message: '删除失败，' + res.data,
              type: 'warning'
            })
          }
          this.getStudentInfo()
        })
      }).catch(() => {

      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
