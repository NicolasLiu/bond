<template>
	<div>
		<div class="container">
			<div class="handle-box">
				<el-button type="primary" icon="Plus" @click="handleAdd">新增</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="id" label="ID" width="40" align="center"></el-table-column>
				<el-table-column prop="name" label="名称" align="center"></el-table-column>
				<el-table-column prop="priority" label="优先级" align="center"></el-table-column>
				<el-table-column prop="bond_credit_limit" label="债券评级限制" align="center"></el-table-column>
				<el-table-column prop="institution_credit_limit" label="主体评级限制" align="center"></el-table-column>

				<el-table-column label="操作" width="220" align="center" fixed="right">
					<template #default="scope">
						<el-button text icon="Edit" @click="handleEdit(scope.$index, scope.row)" v-permiss="15">
							编辑
						</el-button>
						<el-button text icon="Delete" class="red" @click="handleDelete(scope.$index)" v-permiss="16">
							删除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>

		<!-- 编辑弹出框 -->
		<el-dialog title="编辑" v-model="editVisible" width="900px">
			<el-form ref="formRef" :model="form" label-width="0px" style="width: 850px;">
				<el-form-item>
					<el-col :span="12">
						<el-form-item label="名称" label-width="110px" prop="name">
							<el-input v-model="form.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="优先级" label-width="100px" prop="priority">
							<el-input v-model="form.priority"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="24">
						<el-form-item label="债券评级限制" label-width="100px" prop="bond_credit_limit">
							<el-checkbox v-model="checkAllBondCredit" :indeterminate="isIndeterminateBondCredit"
								@change="handleCheckAllBondCredit">
								全选</el-checkbox>
							<el-checkbox-group v-model="form.bond_credit_limit" @change="handleCheckedBondCreditChange">
								<el-checkbox v-for="cre in bondCredits" :key="cre" :label="cre">{{ cre }}</el-checkbox>
							</el-checkbox-group>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="24">
						<el-form-item label="主体评级限制" label-width="100px" prop="institution_credit_limit">
							<el-checkbox v-model="checkAllInstitutionCredit"
								:indeterminate="isIndeterminateInstitutionCredit"
								@change="handleCheckAllInstitutionCredit">
								全选</el-checkbox>
							<el-checkbox-group v-model="form.institution_credit_limit"
								@change="handleCheckedInstitutionCreditChange">
								<el-checkbox v-for="cre in institutionCredits" :key="cre" :label="cre">{{ cre }}
								</el-checkbox>
							</el-checkbox-group>
						</el-form-item>
					</el-col>
				</el-form-item>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">取 消</el-button>
					<el-button @click="saveEdit">确 定</el-button>
				</span>
			</template>
		</el-dialog>

		<!-- 新增弹出框 -->
		<el-dialog title="新增" v-model="addVisible" width="900px">
			<el-form ref="formRef" :model="form" label-width="0px" style="width: 850px;">
				<el-form-item>
					<el-col :span="12">
						<el-form-item label="名称" label-width="110px" prop="name">
							<el-input v-model="form.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="优先级" label-width="100px" prop="priority">
							<el-input v-model="form.priority"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="24">
						<el-form-item label="债券评级限制" label-width="100px" prop="bond_credit_limit">
							<el-checkbox v-model="checkAllBondCredit" :indeterminate="isIndeterminateBondCredit"
								@change="handleCheckAllBondCredit">
								全选</el-checkbox>
							<el-checkbox-group v-model="form.bond_credit_limit" @change="handleCheckedBondCreditChange">
								<el-checkbox v-for="cre in bondCredits" :key="cre" :label="cre">{{ cre }}</el-checkbox>
							</el-checkbox-group>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="24">
						<el-form-item label="主体评级限制" label-width="100px" prop="institution_credit_limit">
							<el-checkbox v-model="checkAllInstitutionCredit"
								:indeterminate="isIndeterminateInstitutionCredit"
								@change="handleCheckAllInstitutionCredit">
								全选</el-checkbox>
							<el-checkbox-group v-model="form.institution_credit_limit"
								@change="handleCheckedInstitutionCreditChange">
								<el-checkbox v-for="cre in institutionCredits" :key="cre" :label="cre">{{ cre }}
								</el-checkbox>
							</el-checkbox-group>
						</el-form-item>
					</el-col>
				</el-form-item>

			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">取 消</el-button>
					<el-button @click="saveAdd">确 定</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import { getAllOpponent, updateOpponent, deleteOpponent, addOpponent } from '../api/index';




interface TableItem {
	id: number;
	name: string;
	priority: number;
	bond_credit_limit: string;
	institution_credit_limit: string;

}

const query = reactive({
	address: '',
	name: '',
	pageIndex: 1,
	pageSize: 10
});

const checkAllBondCredit = ref(false)
const isIndeterminateBondCredit = ref(false)
const bondCredits = [
	'0',
	'C',
	'CC',
	'CCC',
	'B-',
	'B',
	'B+',
	'BB-',
	'BB',
	'BB+',
	'BBB-',
	'BBB',
	'BBB+',
	'A-1',
	'A-',
	'A',
	'A+',
	'AA-',
	'AA',
	'AA+',
	'AAA',
]
const checkAllInstitutionCredit = ref(false)
const isIndeterminateInstitutionCredit = ref(false)
const institutionCredits = [
	'0',
	'C',
	'CC',
	'CCC',
	'CCC+',
	'B-',
	'B',
	'B+',
	'BB-',
	'BB',
	'BB+',
	'BBB-',
	'BBB',
	'BBB+',
	'A-1',
	'A-',
	'A',
	'A+',
	'AA-',
	'AA',
	'AA+',
	'AAA',
	'A1',
	'AApi',
	'AA+pi',
	'A3',
	'AAApi',
	'Ba1',
	'Ba2',
	'Baa1',
	'Baa2',
	'Baa3',
	'Caa1',
]


let form = reactive({
	id: 0,
	name: '',
	priority: 0,
	bond_credit_limit: [] as string[],
	institution_credit_limit: [] as string[]
});

const handleCheckAllBondCredit = (val: boolean) => {
	form.bond_credit_limit = val ? bondCredits : [];
	isIndeterminateBondCredit.value = false;
}
const handleCheckedBondCreditChange = (value: string[]) => {
	const checkedCount = value.length;
	checkAllBondCredit.value = checkedCount === bondCredits.length;
	isIndeterminateBondCredit.value = checkedCount > 0 && checkedCount < bondCredits.length;
}
const handleCheckAllInstitutionCredit = (val: boolean) => {
	form.institution_credit_limit = val ? institutionCredits : [];
	isIndeterminateInstitutionCredit.value = false;
}
const handleCheckedInstitutionCreditChange = (value: string[]) => {
	const checkedCount = value.length;
	checkAllInstitutionCredit.value = checkedCount === institutionCredits.length;
	isIndeterminateInstitutionCredit.value = checkedCount > 0 && checkedCount < institutionCredits.length;
}





const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = () => {
	getAllOpponent().then(res => {
		tableData.value = res.data;
		pageTotal.value = res.data.pageTotal || 50;
	});
};
getData();

// 查询操作
const handleSearch = () => {
	query.pageIndex = 1;
	getData();
};
// 分页导航
const handlePageChange = (val: number) => {
	query.pageIndex = val;
	getData();
};

// 删除操作
const handleDelete = (index: number) => {
	// 二次确认删除
	ElMessageBox.confirm('确定要删除吗？', '提示', {
		type: 'warning'
	}).then(() => {
		deleteOpponent(tableData.value[index].id).then(res => {
			if (res.data === 1) {
				ElMessage.success('删除成功');
				getData();
			}

		});

	}).catch(() => { });
};

// 表格编辑时弹窗和保存
const editVisible = ref(false);
const addVisible = ref(false);

const handleAdd = () => {
	form.id = 0;
	form.name = '';
	form.priority = 100;
	form.bond_credit_limit = [];
	form.institution_credit_limit = [];
	addVisible.value = true;
};

let idx: number = -1;
const handleEdit = (index: number, row: any) => {
	idx = index;
	form.id = row.id;
	form.name = row.name;
	form.priority = row.priority;
	form.bond_credit_limit = row.bond_credit_limit.split(',');
	form.institution_credit_limit = row.institution_credit_limit.split(',');
	editVisible.value = true;
};
const saveEdit = () => {
	editVisible.value = false;
	updateOpponent(form).then(res => {
		if (res.data === 1) {
			ElMessage.success("修改成功");
		}
	});
	tableData.value[idx].name = form.name;
	tableData.value[idx].priority = form.priority;
	tableData.value[idx].bond_credit_limit = form.bond_credit_limit.join(",");
	tableData.value[idx].institution_credit_limit = form.institution_credit_limit.join(",");
	

};
const saveAdd = () => {

	addOpponent(form).then(res => {
		if (res.data === 1) {
			addVisible.value = false;
			ElMessage.success("添加成功");
			getData();
		}
	});


};

</script>

<style scoped>
.handle-box {
	margin-bottom: 20px;
}

.handle-select {
	width: 120px;
}

.handle-input {
	width: 300px;
}

.table {
	width: 100%;
	font-size: 14px;
}

.red {
	color: #ff0000;
}

.green {
	color: #00ff00;
}

.mr10 {
	margin-right: 10px;
}

.table-td-thumb {
	display: block;
	margin: auto;
	width: 40px;
	height: 40px;
}
</style>
