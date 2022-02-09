<!--
描述：学生端，查看已完成考试的成绩以及答案详情。
作者：211027134 叶怀生
创建：2022年2月5日20:15:05
修改：2022年2月10日01:41:37
-->
<template>
  <div class="exam">
    <el-select v-model="termValue" placeholder="请选择">
      <el-option
        v-for="item in terms"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <div style="margin-bottom: 14px"></div>
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="tname" label="考试名称" width="240"></el-table-column>
      <el-table-column prop="starttime" label="考试开始时间" width="200"></el-table-column>
      <el-table-column prop="endtime" label="考试结束时间" width="200"></el-table-column>
      <el-table-column prop="description" label="考试描述" width="640"></el-table-column>
      <!--
      <el-table-column prop="author" label="发布人" width="150"></el-table-column>
      -->
      <el-table-column prop="score" label="" width="100"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="entryExam(scope.row.tname, scope.row.id)" type="primary" size="small" icon="el-icon-more-outline">查看试卷</el-button>
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
      class="page"
    ></el-pagination>
    <el-dialog :title="nowExamTitle" :visible.sync="dialogTableVisible">
      <el-descriptions title="" direction="vertical" :column="4" border>
        <el-descriptions-item label="答题人"><el-tag size="small">{{sname}}</el-tag></el-descriptions-item>
        <el-descriptions-item :label=totalScore><el-tag size="small">{{score}}分</el-tag></el-descriptions-item>
        <el-descriptions-item :label=totalNum :span="2"><el-tag size="small">答对{{successNum}}题</el-tag></el-descriptions-item>
      </el-descriptions>
      <div style="width: 100%; height: 64px;">
        <div class="el-icon-s-order" style="font-size: 16px; font-weight: bold;margin-top: 32px"> 答题解析</div>
        <div style="float: right; margin-top: 32px">
          <el-switch
            v-model="switchState"
            active-color="#ff4949"
            active-text="错题集"
            inactive-text="全部题目">
          </el-switch>
        </div>
      </div>
      <div>
        <div v-for="(question, index) in questions">
          <div v-if="switchState==false || question.success==false">
            <div class="question">{{index+1}}、{{question.title}} <el-tag type="info">分值5分</el-tag></div>
            <div v-if="question.type == 1"> <!-- 单选题 -->
              <!-- [WARNING]如果答案以数组的方式传递进入，以下可以改成for循环的形式 -->
              <div>
                <el-radio style="color: #333333;" v-model=question.selected label="1" @change="selectedMessage(index)" disabled>A、{{question.optionA}}</el-radio>
              </div>
              <div>
                <el-radio style="color: #333333;" v-model=question.selected label="2" @change="selectedMessage(index)" disabled>B、{{question.optionB}}</el-radio>
              </div>
              <div>
                <el-radio style="color: #333333;" v-model=question.selected label="3" @change="selectedMessage(index)" disabled>C、{{question.optionC}}</el-radio>
              </div>
              <div>
                <el-radio style="color: #333333;" v-model=question.selected label="4" @change="selectedMessage(index)" disabled>D、{{question.optionD}}</el-radio>
              </div>
            </div>
            <div v-else><!-- 多选题 -->
              <div>
                <el-checkbox style="color: #333333;" v-model=question.A_selected label="1" @change="selectedMessage(index)" disabled>A、{{question.optionA}}</el-checkbox>
              </div>
              <div>
                <el-checkbox style="color: #333333;" v-model=question.B_selected label="1" @change="selectedMessage(index)" disabled>B、{{question.optionB}}</el-checkbox>
              </div>
              <div>
                <el-checkbox style="color: #333333;" v-model=question.C_selected label="1" @change="selectedMessage(index)" disabled>C、{{question.optionC}}</el-checkbox>
              </div>
              <div>
                <el-checkbox style="color: #333333;" v-model=question.D_selected label="1" @change="selectedMessage(index)" disabled>D、{{question.optionD}}</el-checkbox>
              </div>
            </div>
            <div style="margin-top: 4px" v-if="question.success">
              <el-alert
                show-icon
                title="回答正确"
                type="success"
                :closable="false">
              </el-alert>
            </div>
            <div style="margin-top: 4px" v-else>
              <el-alert
                show-icon
                title="回答错误"
                type="error"
                :closable="false">
              </el-alert>
              <el-alert
                :title=question.tip
                type="info"
                show-icon
                :closable="false">
              </el-alert>
            </div>
            <div class="question-foot"></div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        records: [],
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      questions: [],
      terms: [{
        value: '1',
        label: '全部考试'
      }, {
        value: '2',
        label: '2021-2022-2'
      }],
      termValue: '1',
      dialogTableVisible: false,
      nowExamTitle: '',
      switchState: false,
      sname: '叶怀生', // [WARNING] 注意从cookie中获取
      totalScore: 0,
      score: 100,
      totalNum: 0,
      successNum: 0,
      webSite: 'http://8.130.16.20:8080/' // 站点地址
    };
  },
  // Vue对象内置函数，在此函数处hook，可在页面绘制时执行特定函数
  created() {
    // 分页获取已完成的考试信息
    this.getExamInfo();
  },
  methods: {
    // 查看试卷
    entryExam(name, testId) {
      let submitData = {
        sid: 5, // [WARNING] 注意从cookie中获取
        tid: testId
      };
      this.$axios({
        url: this.webSite + 'Stu/ShowAnswer',
        method: 'post',
        data: submitData
      }).then(res => {
        if(res.data.code == '200'){
          this.questions = res.data.data.answers;
          this.totalNum = '共'+this.questions.length+'题';
          this.totalScore = '总分'+(this.questions.length * 5)+'分';
          this.score = 0;
          this.successNum = 0;
          for(let j = 0, len = this.questions.length; j < len; j++){
            this.questions[j].success = (this.questions[j].myAnswer == this.questions[j].answer);
            if(this.questions[j].success){
              this.successNum += 1;
              this.score += 5;
            }
            if(this.questions[j].type == 1){
              if(this.questions[j].myAnswer == 'A')
                this.questions[j].selected = '1';
              if(this.questions[j].myAnswer == 'B')
                this.questions[j].selected = '2';
              if(this.questions[j].myAnswer == 'C')
                this.questions[j].selected = '3';
              if(this.questions[j].myAnswer == 'D')
                this.questions[j].selected = '4';
            }else{
              if(this.questions[j].myAnswer.indexOf('A') != -1 )
                this.questions[j].A_selected = true;
              if(this.questions[j].myAnswer.indexOf('B') != -1)
                this.questions[j].B_selected = true;
              if(this.questions[j].myAnswer.indexOf('C') != -1)
                this.questions[j].C_selected = true;
              if(this.questions[j].myAnswer.indexOf('D') != -1)
                this.questions[j].D_selected = true;
            }
            this.questions[j].tip = '正确答案：'+this.questions[j].answer+'，'+this.questions[j].analysis;
          }
        }
        else{
          this.$message({
            message: '拉取答题信息失败，' + res.data,
            type: 'warning'
          })
        }
      }).catch(error => {});
      this.nowExamTitle = name;
      this.dialogTableVisible = true;
    },
    // 分页获取已完成的考试信息
    getExamInfo() {
      let submitData = {
        sid: 5, // [WARNING] 注意从cookie中获取
        pageNum: this.pagination.current,
        pageSize: this.pagination.size
      }
      this.$axios({
        url: this.webSite + 'Stu/ShowTestFin',
        method: 'post',
        data: submitData
      }).then(res => {
        if(res.data.code == '200'){
          this.pagination.records = res.data.data.tests;
        }
        else{
          this.$message({
            message: '拉取考试信息失败，' + res.data,
            type: 'warning'
          })
        }
      }).catch(error => {});

    },
    // 改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo();
    },
    // 改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo();
    },
    // Vue表格组件回调函数，使表格各行显示不同叠层样式
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
.exam {
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
    background-color: #DD5862 !important;
  }
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
.el-table .warning-row {
  background: #000 !important;

}

.el-table .success-row {
  background: #DD5862;
}

</style>

