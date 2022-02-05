<!--
描述：学生端，考试页面
作者：211027134 叶怀生
时间：2022年2月5日15:47:55
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
          <div class="question">{{index+1}}、{{question.topic}}</div>
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
      questions: [{
        topic: '这是一个测试题目',
        optionA: '2022年2月5日15:03:40',
        optionB: '2022年2月5日15:03:48',
        optionC: '考试列表测试罢了',
        optionD: '老小子',
        questionCode: 123,
        type: 2,  // 多选题
        A_selected: false,
        B_selected: false,
        C_selected: false,
        D_selected: false,
        selected:'-1'
      },
      {
        topic: '这是两个测试题目',
        optionA: '2022年2月5日15:03:40',
        optionB: '2022年2月5日15:03:48',
        optionC: '考试列表测试罢了',
        optionD: '老小子',
        questionCode: 123,
        type: 1,  // 单选题
        A_selected: false,
        B_selected: false,
        C_selected: false,
        D_selected: false,
        selected:'-1'
      },
      {
        topic: '这是两个测试题目',
        optionA: '2022年2月5日15:03:40',
        optionB: '2022年2月5日15:03:48',
        optionC: '考试列表测试罢了',
        optionD: '老小子',
        questionCode: 123,
        type: 1,  // 单选题
        A_selected: false,
        B_selected: false,
        C_selected: false,
        D_selected: false,
        selected:'-1'
      }],
      buttonLoading: false, // 按钮是否显示未提交状态
      buttonBan: true,    // 按钮是否显示未禁止状态
      totalQuestionNum: 1,
      selectedNum: 0, // 已完成题目数量
      selectedInfo:[],
      buttonTipData:'您当前存在未完成题目，暂时无法交卷。',
      buttonTipType:'warning'
    }
  },
  created() {

    this.totalQuestionNum = this.questions.length;  // 获取题目数量
    for(var index = 0; index < this.totalQuestionNum; index++){
      this.selectedInfo.push(false)
    }
    this.examCode = this.$route.query.examCode; // 获取路由传递的试卷id，用于查询并创建试卷
  },
  methods: {
    // 进度条回调
    format(percentage) {
      return percentage === 100 ? '完成' : `${percentage.toFixed(0)}%`;
    },
    // 提交试卷
    submitExam(){
      this.buttonLoading = true;
      // 提交试卷，在这里使用axios进行post请求
      this.buttonLoading = false;
      // 以下是message弹窗
      const h = this.$createElement;
      this.$msgbox({
        title: '消息',
        message: h('p', null, [
          h('span', null, '试卷提交 '),
          h('i', { style: 'color: teal' }, '成功'),
          h('br', null, null),
          h('span', null, '考试结束后，您可以在 '),
          h('i', { style: 'color: #E6A23C' }, '已完成考试'),
          h('span', null, ' 中查看您的答题成绩')
        ]),
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
          this.$router.push('/stuNewExam');
        }
      }).then(action => {
        /*this.$message({
          type: 'info',
          message: 'action: ' + action
        });*/

      });
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
