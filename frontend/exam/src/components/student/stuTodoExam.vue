<!--
描述：学生端，考试页面
作者：211027134 叶怀生
创建：2022年2月5日15:47:55
修改：2022年2月9日16:58:58
-->
<template>
  <el-container class="conatiner">
    <el-header>
      <span style="margin-left: 40px">已完成：{{ selectedNum }}/{{totalQuestionNum}}</span>
      <el-progress :percentage="selectedNum/totalQuestionNum*100" :format="format" style="margin-left: 40px"></el-progress>
    </el-header>
    <el-main>
      <div class="exam">
        <div v-for="(question, index) in questions">
          <!-- 增加分数显示 -->
          <!-- <div class="question">{{index+1}}、{{question.topic}} <el-tag>5分</el-tag></div> -->
          <div class="question">{{index+1}}、{{question.title}}</div>
          <div v-if="question.type == 1"> <!-- 单选题 -->
            <!-- [WARNING]如果答案以数组的方式传递进入，以下可以改成for循环的形式 -->
            <div>
              <el-radio style="color: #333333;" v-model=question.selected label="1" @change="selectedMessage(index)">A、{{question.optionA}}</el-radio>
            </div>
            <div>
              <el-radio style="color: #333333;" v-model=question.selected label="2" @change="selectedMessage(index)">B、{{question.optionB}}</el-radio>
            </div>
            <div>
              <el-radio style="color: #333333;" v-model=question.selected label="3" @change="selectedMessage(index)">C、{{question.optionC}}</el-radio>
            </div>
            <div>
              <el-radio style="color: #333333;" v-model=question.selected label="4" @change="selectedMessage(index)">D、{{question.optionD}}</el-radio>
            </div>
          </div>
          <div v-else><!-- 多选题 -->
            <div>
              <el-checkbox style="color: #333333;" v-model=question.A_selected label="1" @change="selectedMessage(index)">A、{{question.optionA}}</el-checkbox>
            </div>
            <div>
              <el-checkbox style="color: #333333;" v-model=question.B_selected label="1" @change="selectedMessage(index)">B、{{question.optionB}}</el-checkbox>
            </div>
            <div>
              <el-checkbox style="color: #333333;" v-model=question.C_selected label="1" @change="selectedMessage(index)">C、{{question.optionC}}</el-checkbox>
            </div>
            <div>
              <el-checkbox style="color: #333333;" v-model=question.D_selected label="1" @change="selectedMessage(index)">D、{{question.optionD}}</el-checkbox>
            </div>
          </div>
          <div class="question-foot"></div>
        </div>
      </div>
    </el-main>
    <el-footer>
      <el-button type="primary" round :loading="buttonLoading" :disabled="buttonBan" @click="submitExam()">交卷</el-button>
      <el-alert
        :title="buttonTipData"
        :type="buttonTipType"
        :closable="false"
        class="button-tip">
      </el-alert>
    </el-footer>
  </el-container>

</template>

<script>
export default {
  name: "stuTodoExam",
  data() {
    return{
      questions: [],
      buttonLoading: false, // 按钮是否显示未提交状态
      buttonBan: true,    // 按钮是否显示未禁止状态
      totalQuestionNum: 1,
      selectedNum: 0, // 已完成题目数量
      selectedInfo:[],
      buttonTipData:'您当前存在未完成题目，暂时无法交卷。',
      buttonTipType:'warning',
      webSite: 'http://8.130.16.20:8080/' // 站点地址
    }
  },
  created() {
    this.tid = this.$route.query.tid; // 获取路由传递的试卷id，用于查询并创建试卷
    this.pid = this.$route.query.pid;
    this.sid = this.$route.query.sid;
    this.totalQuestionNum = 1;
    this.getQuestionList();
  },
  methods: {
    // 进度条回调
    format(percentage) {
      return percentage === 100 ? '完成' : `${percentage.toFixed(0)}%`;
    },
    // 获取考试信息列表
    getQuestionList(){
      if(this.tid >= 0){
        this.$axios({
          url: this.webSite + 'Stu/StartExam',
          method: 'post',
          data: {tid: this.tid}
        }).then(res => {
          if(res.data.code == '200'){
            this.questions = res.data.data.questions;
            console.log('num', this.questions.length);
            this.totalQuestionNum = Math.max(this.questions.length, 1);  // 获取题目数量
            for(let index = 0; index < this.totalQuestionNum; index++){
              this.selectedInfo.push(false);
              console.log(this.questions[index]);
            }
          }
          else{
            this.$message({
              message: '拉取考试信息失败，' + res.data,
              type: 'warning'
            })
          }
        })
          .catch(error => {});
      }
    },
    // 提交试卷
    submitExam(){
      this.buttonLoading = true;
      // 提交试卷，在这里使用axios进行post请求
      let submitData = [];
      for(let j = 0, len = this.questions.length; j < len; j++){
        // 生成答案数据
        let answer = '';
        let ABCD = ['A', 'B', 'C', 'D'];
        if(this.questions[j].type == 1){
          answer = ABCD[this.questions[j].selected - 1];
        }else{
          if(this.questions[j].A_selected)
            answer += 'A';
          if(this.questions[j].B_selected)
            answer += 'B';
          if(this.questions[j].C_selected)
            answer += 'C';
          if(this.questions[j].D_selected)
            answer += 'D';
        }
        submitData.push({
          tid: this.tid,
          pid: this.pid,
          qid: this.questions[j].id,
          sid: this.sid,
          answer: answer
        });
      }
      let success = false;
      let infoData = '';
      this.$axios({
        url: this.webSite + 'Stu/SubmitTest',
        method: 'post',
        data: submitData
      }).then(res => {
        if(res.data.code == '200'){
          success = true;
        }
        else{
          infoData = res.data;
        }
        // 以下是message弹窗与路由跳转
        const h = this.$createElement;
        let msgInfo = null;
        if(success == true){
          msgInfo = h('p', null, [
            h('span', null, '试卷提交 '),
            h('i', { style: 'color: teal' }, '成功'),
            h('br', null, null),
            h('span', null, '考试结束后，您可以在 '),
            h('i', { style: 'color: #E6A23C' }, '已完成考试'),
            h('span', null, ' 中查看您的答题成绩')
          ]);

        }else{
          msgInfo = h('p', null, [
            h('span', null, '试卷提交 '),
            h('i', { style: 'color: red' }, '失败'),
            h('br', null, null),
            h('span', null, infoData),
          ]);
        }

        // [WAIT FIX] 这里的提示有点问题，一直提示失败，即使提交成功。
        this.$msgbox({
          title: '消息',
          message: msgInfo,
          showCancelButton: false,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            /*
            if (action === 'confirm') {
              instance.confirmButtonLoading = false;
              instance.confirmButtonText = '执行中...';
              setTimeout(() => {
                done();
                setTimeout(() => {
                  instance.confirmButtonLoading = false;
                }, 300);
              }, 3000);
            } else {
              done();
            }
            */
            done();
            if(success)
              this.$router.push('/stuNewExam');
          }
        }).then(action => {
          /*this.$message({
            type: 'info',
            message: 'action: ' + action
          });*/

        });
      })
      this.buttonLoading = false;
    },
    // 答题回调
    selectedMessage(index) {
      var questionType = this.questions[index].type;
      if(questionType == 1){
        if(this.selectedInfo[index] == false) {
          this.selectedInfo[index] = true;
          this.selectedNum++;
        }
      }
      else{
        var A = !this.questions[index].A_selected;
        var B = !this.questions[index].B_selected;
        var C = !this.questions[index].C_selected;
        var D = !this.questions[index].D_selected;
        var new_state = !(A&&B&&C&&D);
        if(this.selectedInfo[index] != new_state){
          this.selectedInfo[index] = new_state;
          if(new_state)
            this.selectedNum++;
          else
            this.selectedNum--;
        }
      }
      var checkIndex = -1
      for(var index = 0; index < this.totalQuestionNum; index++){
        if(this.selectedInfo[index] == false){
          checkIndex = index;
          break;
        }

      }
      if(this.selectedNum == this.totalQuestionNum) {
        this.buttonTipData = '您已完成全部的'+this.totalQuestionNum+'题，可以提交试卷啦！';
        this.buttonTipType = 'success';
        this.buttonBan = false;
      }
      else{
        this.buttonTipData = '您还未作答 第'+(checkIndex+1)+'题 ，暂时无法交卷。';
        this.buttonTipType = 'warning';
        this.buttonBan = true;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .conatiner{
    height: 780px;
    margin: 0px 40px;
  }
  .exam {
    padding: 0px 160px;
    .question{
      padding-bottom: 4px;
      height: auto;
      color: #444444;
      font-size: 18px;
      line-height: 20px;
      font-weight: bold;
    }
    .question-foot{
      height: 40px;
    }
    .option{
      padding: 2px 0px;
    }
  }
  .el-header {

    color: #333;
    line-height: 20px;
  }

  .el-aside {
    color: #333;
  }
  .el-footer .el-button{
    display:block;margin:0 auto;
  }
  .el-footer .button-tip{
    margin-top: 18px;
    margin-left: 36px;
  }
</style>
