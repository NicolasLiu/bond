<template>
	<div>
		<div class="container">
			<div class="handle-box">
				<el-button type="primary" icon="Plus" @click="handleAdd">新增</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="id" label="ID" width="40" align="center"></el-table-column>
				<el-table-column prop="status" label="状态" align="center"></el-table-column>
				<el-table-column prop="value" label="交易金额（万元）" align="center"></el-table-column>
				<el-table-column prop="discount_rate" label="折价率" align="center"></el-table-column>
				<el-table-column prop="financing_type" label="融资类型" align="center"></el-table-column>
				<el-table-column prop="financing_rate" label="融资利率" align="center"></el-table-column>
				<el-table-column prop="opponent" label="交易对手" align="center"></el-table-column>
				<el-table-column prop="trader" label="对方交易员" align="center"></el-table-column>
				<el-table-column prop="clearing_speed" label="清算速度" align="center"></el-table-column>
				<el-table-column prop="initial_settlement_method" label="首期结算方式" align="center"></el-table-column>
				<el-table-column prop="expiry_settlement_method" label="到期结算方式" align="center"></el-table-column>
				<el-table-column prop="duration" width="60" label="意向期限" align="center"></el-table-column>
				<el-table-column prop="recording_day" width="100" label="录入日" align="center"></el-table-column>
				<el-table-column prop="trading_day" width="100" label="交易日" align="center"></el-table-column>
				<el-table-column prop="maturity_day" width="100" label="到期日" align="center"></el-table-column>
				<el-table-column prop="account.name" label="录入人" align="center"></el-table-column>
				<el-table-column prop="emergency" label="是否紧急" align="center">
					<template #default="scope">
						<el-tag v-if="scope.row.emergency === 1" type="danger" effect="dark" round>紧急</el-tag>
						<el-tag v-if="scope.row.emergency === 0" type="success" effect="dark" round>正常</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="aim" label="交易目的" align="center"></el-table-column>
				<el-table-column prop="mark" label="备注" align="center"></el-table-column>
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
					<el-col :span="8">
						<el-form-item label="融资/借贷账户" label-width="110px" prop="account">
							<el-select v-model="form.account" placeholder="请选择">
								<el-option key="1" label="lcd_排券_银行间" :value="1"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="交易对手" label-width="100px" prop="opponent">
							<el-input v-model="form.opponent"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="对方交易员" label-width="100px" prop="trader">
							<el-input v-model="form.trader"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="8">
						<el-form-item label="融资类型" label-width="100px" prop="financing_type">
							<el-select v-model="form.financing_type" placeholder="请选择">
								<el-option label="质押式回购" value="质押式回购"></el-option>
								<el-option label="买断式回购" value="买断式回购"></el-option>
								<el-option label="开放式回购" value="开放式回购"></el-option>
								<el-option label="债券借入" value="债券借入"></el-option>
								<el-option label="同业拆入" value="同业拆入"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="融资利率" label-width="100px" prop="financing_rate">
							<el-input v-model="form.financing_rate"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="清算速度" label-width="100px" prop="clearing_speed">
							<el-select v-model="form.clearing_speed" placeholder="请选择">
								<el-option label="首到期T+0" value="首到期T+0"></el-option>
								<el-option label="首期T+0,到期T+1" value="首期T+0,到期T+1"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="16">
						<el-form-item label="起止日期" label-width="100px" prop="date">
							<el-date-picker v-model="form.date" type="daterange" range-separator="到"
								start-placeholder="交易日" end-placeholder="到期日" @change="calcDuration()" />
						</el-form-item>

					</el-col>
					<el-col :span="8">
						<el-form-item label="意向期限(天)" label-width="100px" prop="duration">
							<el-input disabled v-model="form.duration"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="8">
						<el-form-item label="金额(万元)" label-width="100px" prop="value">
							<el-input v-model="form.value"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="首期结算方式" label-width="100px" prop="initial_settlement_method">
							<el-select v-model="form.initial_settlement_method" placeholder="请选择">
								<el-option label="DVP" value="DVP"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="到期结算方式" label-width="100px" prop="expiry_settlement_method">
							<el-select v-model="form.expiry_settlement_method" placeholder="请选择">
								<el-option label="DVP" value="DVP"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>

					<el-col :span="8">
						<el-form-item label="折价率" label-width="100px" prop="discount_rate">
							<el-input v-model="form.discount_rate" placeholder="e.g. 0.8"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="交易目的" label-width="100px" prop="aim">
							<el-select v-model="form.aim" placeholder="请选择">
								<el-option label="交易" value="交易"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="备注" label-width="100px" prop="mark">
							<el-input type="textarea" :autosize="{ minRows: 1, maxRows: 4 }" v-model="form.mark">
							</el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="8">
						<el-form-item label="是否紧急" label-width="100px" prop="emergency">
							<el-switch v-model="form.emergency" :active-value="1" :inactive-value="0"></el-switch>
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
					<el-col :span="8">
						<el-form-item label="融资/借贷账户" label-width="110px" prop="account">
							<el-select v-model="form.account" placeholder="请选择">
								<el-option key="1" label="lcd_排券_银行间" :value="1"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="交易对手" label-width="100px" prop="opponent">
							<el-input v-model="form.opponent"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="对方交易员" label-width="100px" prop="trader">
							<el-input v-model="form.trader"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="8">
						<el-form-item label="融资类型" label-width="100px" prop="financing_type">
							<el-select v-model="form.financing_type" placeholder="请选择">
								<el-option label="质押式回购" value="质押式回购"></el-option>
								<el-option label="买断式回购" value="买断式回购"></el-option>
								<el-option label="开放式回购" value="开放式回购"></el-option>
								<el-option label="债券借入" value="债券借入"></el-option>
								<el-option label="同业拆入" value="同业拆入"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="融资利率" label-width="100px" prop="financing_rate">
							<el-input v-model="form.financing_rate"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="清算速度" label-width="100px" prop="clearing_speed">
							<el-select v-model="form.clearing_speed" placeholder="请选择">
								<el-option label="首到期T+0" value="首到期T+0"></el-option>
								<el-option label="首期T+0,到期T+1" value="首期T+0,到期T+1"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="16">
						<el-form-item label="起止日期" label-width="100px" prop="date">
							<el-date-picker v-model="form.date" type="daterange" range-separator="到"
								start-placeholder="交易日" end-placeholder="到期日" @change="calcDuration()" />
						</el-form-item>

					</el-col>
					<el-col :span="8">
						<el-form-item label="意向期限(天)" label-width="100px" prop="duration">
							<el-input disabled v-model="form.duration"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="8">
						<el-form-item label="金额(万元)" label-width="100px" prop="value">
							<el-input v-model="form.value"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="首期结算方式" label-width="100px" prop="initial_settlement_method">
							<el-select v-model="form.initial_settlement_method" placeholder="请选择">
								<el-option label="DVP" value="DVP"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="到期结算方式" label-width="100px" prop="expiry_settlement_method">
							<el-select v-model="form.expiry_settlement_method" placeholder="请选择">
								<el-option label="DVP" value="DVP"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>

					<el-col :span="8">
						<el-form-item label="折价率" label-width="100px" prop="discount_rate">
							<el-input v-model="form.discount_rate" placeholder="e.g. 0.8"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="交易目的" label-width="100px" prop="aim">
							<el-select v-model="form.aim" placeholder="请选择">
								<el-option label="交易" value="交易"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="备注" label-width="100px" prop="mark">
							<el-input type="textarea" :autosize="{ minRows: 1, maxRows: 4 }" v-model="form.mark">
							</el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="8">
						<el-form-item label="是否紧急" label-width="100px" prop="emergency">
							<el-switch v-model="form.emergency" :active-value="1" :inactive-value="0"></el-switch>
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
import { getAllApply, updateApply, deleteApply, addApply } from '../api/index';


interface Account {
	id: number;
	name: string;
	internal_account_name: string;
	internal_account_code: string;
	external_account_name: string;
	external_account_code: string;
}

interface TableItem {
	id: number;
	account: Account;
	status: string;
	opponent: string;
	temporary_opponent: string;
	trader: string;
	discount_rate: number;
	financing_type: string;
	financing_rate: number;
	clearing_speed: string;
	initial_settlement_method: string;
	expiry_settlement_method: string;
	value: number;
	duration: number;
	recording_day: string;
	trading_day: string;
	maturity_day: string;
	aim: string;
	mark: string;
	emergency: number;
}

const query = reactive({
	address: '',
	name: '',
	pageIndex: 1,
	pageSize: 10
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// 获取表格数据
const getData = () => {
	getAllApply().then(res => {
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
		deleteApply(tableData.value[index].id).then(res => {
			if (res.data === 1) {
				ElMessage.success('删除成功');
				// tableData.value.splice(index, 1);
				getData();
			}

		});

	}).catch(() => { });
};

// 表格编辑时弹窗和保存
const editVisible = ref(false);
const addVisible = ref(false);
let form = reactive({
	id: 0,
	account: 0,
	status: '',
	opponent: '',
	date: [new Date(), new Date()],
	temporary_opponent: '',
	trader: '',
	discount_rate: 1,
	financing_type: '',
	financing_rate: 0,
	clearing_speed: '',
	initial_settlement_method: '',
	expiry_settlement_method: '',
	value: 0,
	duration: 0,
	recording_day: '',
	trading_day: '',
	maturity_day: '',
	aim: '',
	mark: '',
	emergency: 0,
});
const handleAdd = () => {
	form.id = 0;
	form.account = 1;
	form.status = '待排券';
	form.opponent = '';
	form.trader = '';
	form.temporary_opponent = '';
	form.discount_rate = 1;
	form.financing_type = '';
	form.financing_rate = 0;
	form.clearing_speed = '';
	form.initial_settlement_method = '';
	form.expiry_settlement_method = '';
	form.value = 0;
	form.duration = 0;
	form.aim = '';
	form.mark = '';
	form.emergency = 0;
	form.recording_day = new Date().toLocaleDateString();
	form.date = [new Date(), new Date()]
	addVisible.value = true;
};

let idx: number = -1;
const handleEdit = (index: number, row: any) => {
	idx = index;
	form.id = row.id;
	form.account = row.account.id;
	form.status = row.status;
	form.opponent = row.opponent;
	form.trader = row.trader;
	form.temporary_opponent = row.temporary_opponent;
	form.discount_rate = row.discount_rate;
	form.financing_type = row.financing_type;
	form.financing_rate = row.financing_rate;
	form.clearing_speed = row.clearing_speed;
	form.initial_settlement_method = row.initial_settlement_method;
	form.expiry_settlement_method = row.expiry_settlement_method;
	form.value = row.value;
	form.duration = row.duration;
	form.aim = row.aim;
	form.mark = row.mark;
	form.emergency = row.emergency;
	form.recording_day = row.recording_day;
	form.date = [new Date(row.trading_day), new Date(row.maturity_day)]
	editVisible.value = true;
};
const saveEdit = () => {
	editVisible.value = false;
	form.trading_day = form.date[0].toLocaleDateString();
	form.maturity_day = form.date[1].toLocaleDateString();
	updateApply(form).then(res => {
		if (res.data === 1) {
			ElMessage.success("修改成功");
		}
	});
	tableData.value[idx].status = form.status;
	tableData.value[idx].opponent = form.opponent;
	tableData.value[idx].trader = form.trader;
	tableData.value[idx].temporary_opponent = form.temporary_opponent;
	tableData.value[idx].discount_rate = form.discount_rate;
	tableData.value[idx].financing_type = form.financing_type;
	tableData.value[idx].financing_rate = form.financing_rate;
	tableData.value[idx].clearing_speed = form.clearing_speed;
	tableData.value[idx].initial_settlement_method = form.initial_settlement_method;
	tableData.value[idx].expiry_settlement_method = form.expiry_settlement_method;
	tableData.value[idx].value = form.value;
	tableData.value[idx].duration = form.duration;
	tableData.value[idx].aim = form.aim;
	tableData.value[idx].mark = form.mark;
	tableData.value[idx].emergency = form.emergency;
	tableData.value[idx].trading_day = form.trading_day;
	tableData.value[idx].maturity_day = form.maturity_day;

};
const saveAdd = () => {

	form.trading_day = form.date[0].toLocaleDateString();
	form.maturity_day = form.date[1].toLocaleDateString();
	addApply(form).then(res => {
		if (res.data === 1) {
			addVisible.value = false;
			ElMessage.success("添加成功");
			getData();
		}
	});


};
const calcDuration = () => {
	if (!form.date) return;
	var day = (form.date[1].getTime() - form.date[0].getTime()) / (1000 * 60 * 60 * 24);
	form.duration = day;
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
