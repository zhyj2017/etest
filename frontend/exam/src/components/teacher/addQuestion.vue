<!-- 添加题目 -->
<template>
  <section class="add">
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
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button type="text" @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  data() {
    return {
      form: { //表单数据初始化
        source: null,
        examDate: null,
        totalTime: null,
        totalScore: null,
        paperId: null,
      }
    };
  },
  methods: {
    formatTime(date) { //日期格式化
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    onSubmit() {
      let examDate = this.formatTime(this.form.examDate)
      this.form.examDate = examDate.substr(0,10)
      this.$axios(`/api/examManagePaperId`).then(res => {
        this.form.paperId = res.data.data.paperId + 1 //实现paperId自增1
        this.$axios({
          url: '/api/exam',
          method: 'post',
          data: {
            ...this.form
          }
        }).then(res => {
          if(res.data.code == 200) {
            this.$message({
              message: '数据添加成功',
              type: 'success'
            })
            this.$router.push({path: '/selectQuestion'})
          }
        })
      })
    },
    cancel() { //取消按钮
      this.form = {}
    },
    
  }
};
</script>
<style lang="scss" scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>

