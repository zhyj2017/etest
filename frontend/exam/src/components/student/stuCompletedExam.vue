<!--
描述：学生端，查看已完成考试的成绩以及答案详情。
作者：211027134 叶怀生
创建：2022年2月5日20:15:05
修改：2022年2月6日05:26:24
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
      <el-table-column fixed="left" prop="name" label="考试名称" width="240"></el-table-column>
      <el-table-column prop="end" label="考试结束时间" width="200"></el-table-column>
      <el-table-column prop="description" label="考试描述" width="640"></el-table-column>
      <el-table-column prop="author" label="发布人" width="150"></el-table-column>
      <el-table-column prop="score" label="得分" width="100"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="entryExam(scope.row.name, scope.row.examCode)" type="primary" size="small">查看试卷</el-button>
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
        <el-descriptions-item label="答题人"><el-tag size="small">叶怀生</el-tag></el-descriptions-item>
        <el-descriptions-item label="总分100分"><el-tag size="small">100分</el-tag></el-descriptions-item>
        <el-descriptions-item label="共20题" :span="2"><el-tag size="small">答对7题</el-tag></el-descriptions-item>
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
            <div class="question">{{index+1}}、{{question.topic}} <el-tag type="info">分值5分</el-tag></div>
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
        records: [{
          name: '测试数据',
          end: '2022年2月5日15:03:48',
          description: '考试列表测试罢了',
          author: '老小子',
          score: 100,
          examCode: 123
        }],
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      questions: [{
        topic: '这是一个测试题目',
        optionA: '2022年2月5日15:03:40',
        optionB: '2022年2月5日15:03:48',
        optionC: '考试列表测试罢了',
        optionD: '老小子',
        questionCode: 123,
        type: 2,  // 多选题
        A_selected: true,
        B_selected: false,
        C_selected: true,
        D_selected: false,
        selected:'-1',
        success: false,
        tip: '正确答案：A，因为A就是对的啊！'
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
        selected:'1',
        success: true,
        tip: '正确答案：A，因为A就是对的啊！'
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
        selected:'2',
        success: false,
        tip: '正确答案：A，因为A就是对的啊！'
      }],
      terms: [{
        value: '1',
        label: '全部考试'
      }, {
        value: '2',
        label: '2021-2022-2'
      }],
      termValue: '1',
      gridData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }],
      dialogTableVisible: false,
      nowExamTitle: '',
      nowExamCode: -1,
      switchState: false
    };
  },
  created() {
    this.getPageInfo();
  },
  methods: {
    entryExam(name, examCode) { // 查看试卷
      this.nowExamTitle = name;
      this.nowExamCode = examCode;
      this.dialogTableVisible = true;
    },
    getPageInfo() {
      // 分页查询考试信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}`
      )
        .then(res => {
          this.pagination = res.data.data;
          console.log(res);
        })
        .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getPageInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getPageInfo();
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

