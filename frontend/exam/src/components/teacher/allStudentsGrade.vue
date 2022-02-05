<!--
描述：查看所有成绩
作者：211027134 叶怀生
时间：2022年1月30日15:50:32
修改：2022年2月6日05:25:49
-->
<template>
  <div class="all">
    <el-select v-model="classValue" filterable placeholder="请选择班级后进行查询">
      <el-option
        v-for="item in classList"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <el-tooltip class="item" effect="dark" content="查询指定班级的成绩" placement="top">
      <el-button icon="el-icon-search" circle style="margin-left: 4px;"></el-button>
    </el-tooltip>
    <div style="margin-bottom: 14px"></div>
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="testName" label="试卷名称" width="300"></el-table-column>
      <el-table-column prop="studentNo" label="学生学号" width="200"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" width="200"></el-table-column>
      <el-table-column prop="score" label="学生成绩" width="200"></el-table-column>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6, //每页条数
      },
      form: {}, //保存点击以后当前考生的信息
      classList: [{
        value: '1',
        label: '2021级电子信息1班'
      },{
        value: '2',
        label: '2021级电子信息2班'
      }, {
        value: '3',
        label: '2021级电子信息3班'
      },{
        value: '4',
        label: '2021级电子信息4班'
      }],
      classValue: '',
    };
  },
  created() {
    this.getClassScoreInfo();
  },
  methods: {
    getClassScoreInfo() {
      //分页查询指定班级成绩
      this.$axios(`/api/test/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data;
      }).catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getClassScoreInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getClassScoreInfo();
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
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
