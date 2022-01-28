//查询所有考试
<template>
  <div class="exam">
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="title" label="题目内容" width="300"></el-table-column>
      <el-table-column prop="type" label="题目类型" width="150"></el-table-column>
      <el-table-column prop="optionA" label="选项A" width="150"></el-table-column>
      <el-table-column prop="optionB" label="选项B" width="150"></el-table-column>
      <el-table-column prop="optionC" label="选项C" width="150"></el-table-column>
      <el-table-column prop="optionD" label="选项D" width="150"></el-table-column>
      <el-table-column prop="answer" label="题目答案" width="150"></el-table-column>
      <el-table-column prop="analysis" label="题目分析" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row.examCode)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteRecord(scope.row.examCode)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10, 20]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑题目信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="题目内容">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="题目类型">
            <el-input v-model="form.type"></el-input>
          </el-form-item>
          <el-form-item label="选项A">
            <el-input v-model="form.optionA"></el-input>
          </el-form-item>
          <el-form-item label="选项B">
            <el-input v-model="form.optionB"></el-input>
          </el-form-item>
          <el-form-item label="选项C">
            <el-input v-model="form.optionC"></el-input>
          </el-form-item>
          <el-form-item label="选项D">
            <el-input v-model="form.optionD"></el-input>
          </el-form-item>
          <el-form-item label="答案">
            <el-input v-model="form.answer"></el-input>
          </el-form-item>
          <el-form-item label="分析">
            <el-input v-model="form.analysis"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {}, //保存点击以后当前试卷的信息
      pagination: { //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
      dialogVisible: false
    }
  },
  created() {
    this.getExamInfo()
  },
  methods: {
    edit(examCode) { //编辑试卷
      this.dialogVisible = true
      this.$axios(`/api/exam/${examCode}`).then(res => { //根据试卷id请求后台
        if(res.data.code == 200) {
          this.form = res.data.data
        }
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    submit() { //提交修改后的试卷信息
      this.dialogVisible = false
      this.$axios({
        url: '/api/exam',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.code == 200) {
          this.$message({ //成功修改提示
            message: '更新成功',
            type: 'success'
          })
        }
        this.getExamInfo()
      })
    },
    deleteRecord(examCode) {
      this.$confirm("确定删除该记录吗,该操作不可逆！！！","删除提示",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: `/api/exam/${examCode}`,
          method: 'delete',
        }).then(res => {
          this.getExamInfo()
        })
      }).catch(() => {

      })
    },
    getExamInfo() { //分页查询所有试卷信息
      this.$axios(`/api/exams/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data
      }).catch(error => {
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val
      this.getExamInfo()
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getExamInfo()
    },
  },
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit{
    margin-left: 20px;
  }
}
</style>
