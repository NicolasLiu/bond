<template>
	<div>
		<div class="container">
			<el-card class="box-card">
				<template #header>
					<div class="card-header">
						<span>融资意向</span>
						<el-button type="primary" icon="Finished" @click="autoManage">自动排券</el-button>
					</div>
				</template>
				<el-table :data="applyData" border class="table" ref="applyTable" header-cell-class-name="table-header"
					highlight-current-row @current-change="handleApplySelect" :max-height="400">
					<el-table-column prop="id" label="ID" width="40" align="center"></el-table-column>
					<el-table-column prop="status" label="状态" align="center" :filters="[
					  { text: '待排券', value: '待排券' },
					  { text: '已排券', value: '已排券' },
					]" :filter-method="filterApplyStatus">
						<template #default="scope">
							<el-tag v-if="scope.row.status === '待排券'" effect="dark" round>{{ scope.row.status }}
							</el-tag>
							<el-tag v-if="scope.row.status === '已排券'" type="success" effect="dark" round>{{
							scope.row.status }}</el-tag>
						</template>
					</el-table-column>
					<el-table-column prop="value" label="交易金额（万元）" align="center" sortable></el-table-column>
					<el-table-column prop="financing_type" label="融资类型" align="center"></el-table-column>
					<el-table-column prop="financing_rate" label="融资利率" align="center" sortable></el-table-column>
					<el-table-column prop="opponent" label="交易对手" align="center"></el-table-column>
					<el-table-column prop="trader" label="对方交易员" align="center"></el-table-column>
					<el-table-column prop="clearing_speed" label="清算速度" align="center"></el-table-column>
					<el-table-column prop="initial_settlement_method" label="首期结算方式" align="center"></el-table-column>
					<el-table-column prop="expiry_settlement_method" label="到期结算方式" align="center"></el-table-column>
					<el-table-column prop="duration" width="60" label="意向期限" align="center"></el-table-column>
					<el-table-column prop="recording_day" width="100" label="录入日" align="center" sortable>
					</el-table-column>
					<el-table-column prop="trading_day" width="100" label="交易日" align="center" sortable>
					</el-table-column>
					<el-table-column prop="maturity_day" width="100" label="到期日" align="center"></el-table-column>
					<el-table-column prop="account.name" label="录入人" align="center"></el-table-column>
					<el-table-column prop="emergency" label="是否紧急" align="center" :filters="[
					  { text: '紧急', value: 1 },
					  { text: '正常', value: 0 },
					]" :filter-method="filterApplyEmergency">
						<template #default="scope">
							<el-tag v-if="scope.row.emergency === 1" type="danger" effect="dark" round>紧急</el-tag>
							<el-tag v-if="scope.row.emergency === 0" type="success" effect="dark" round>正常</el-tag>
						</template>
					</el-table-column>
					<el-table-column prop="aim" label="交易目的" align="center"></el-table-column>
					<el-table-column prop="mark" label="备注" align="center"></el-table-column>

				</el-table>

			</el-card>
			<el-row>

				<el-col :span="12">
					<el-card class="box-card">
						<template #header>
							<div class="card-header">
								<span>持仓情况</span>
								<el-upload ref="uploadRef" accept=".xls, .xlsx" v-model:file-list="fileList"
									action="http://127.0.0.1:8080/position/upload" :limit="1"
									:auto-upload="false" :on-success="uploadSuccess">
									<template #trigger>
										<el-button type="primary" style="margin-right:10px;">选择持仓文件</el-button>
									</template>
									<el-button type="success" @click="submitUpload" v-if="fileList.length > 0">
										更新持仓
									</el-button>
								</el-upload>
							</div>
						</template>
						<el-table :data="positionData" border class="table" ref="positionTable"
							header-cell-class-name="table-header" :max-height="400">
							<el-table-column prop="bond_info.bond_code" label="债券代码" align="center"></el-table-column>
							<el-table-column prop="bond_info.short_name" label="债券名称" width="150" align="center" ></el-table-column>
							<el-table-column prop="account.name" label="账户名称" width="100" align="center">
							</el-table-column>
							<el-table-column prop="value" label="可用金额（万元）" width="100" align="center" sortable>
							</el-table-column>
							<el-table-column prop="status" label="状态" width="100" align="center" :filters="[
							  { text: '可用', value: '可用' },
							  { text: '今日到期', value: '今日到期' },
							]" :filter-method="filterPositionStatus"></el-table-column>
							
							<el-table-column prop="bond_info.institution_credit" label="主体评级" align="center" :filters="[
							  { text: 'AA', value: 'AA' },
							  { text: 'AA+', value: 'AA+' },
							  { text: 'AAA', value: 'AAA' },
							]" :filter-method="filterPositionInstitutionCredit"></el-table-column>
							<el-table-column prop="bond_info.bond_credit" label="债项评级" align="center" :filters="[
							  { text: 'AA', value: 'AA' },
							  { text: 'AA+', value: 'AA+' },
							  { text: 'AAA', value: 'AAA' },
							]" :filter-method="filterPositionBondCredit"></el-table-column>
							<el-table-column prop="bond_info.bond_type" label="债券类型" align="center" :filters="[
							  { text: '国债', value: '国债' },
							  { text: '地方政府债', value: '地方政府债' },
							  { text: '政策性金融债', value: '政策性金融债' },
							  { text: '政府支持债券', value: '政府支持债券' },
							  { text: '企业债', value: '企业债' },
							]" :filter-method="filterPositionType"></el-table-column>
							<el-table-column prop="bond_info.maturity" width="100" label="到期日" align="center">
							</el-table-column>



							<el-table-column label="操作" width="110" align="center" fixed="right">
								<template #default="scope">
									<el-button text icon="Check" class="red" @click="handleUse(scope.$index, scope.row)">
										使用
									</el-button>
								</template>
							</el-table-column>
						</el-table>
					</el-card>
				</el-col>

				<el-col :span="12">
					<el-card header="分配情况" class="box-card">
						<el-table :data="allocationData" border class="table" ref=""
							header-cell-class-name="table-header">
							<el-table-column prop="position.code" label="债券代码" align="center"></el-table-column>
							<el-table-column prop="position.name" label="债券名称" align="center"></el-table-column>
							<el-table-column prop="value" label="分配金额（万元）" align="center"></el-table-column>


							<el-table-column label="操作" width="220" align="center" fixed="right">
								<template #default="scope">
									<el-button v-if="scope.row.apply.status === '待排券'" text icon="Edit"
										@click="handleEditAllocation(scope.$index, scope.row)">
										修改
									</el-button>
									<el-button v-if="scope.row.apply.status === '待排券'" text icon="Delete" class="red"
										@click="handleDelete(scope.$index)" v-permiss="16">
										删除
									</el-button>
								</template>
							</el-table-column>
						</el-table>
						<div class="handle-box">
							<el-button v-if="selectedApply != undefined && selectedApply.status == '待排券'" icon="Check"
								type="primary" @click="handleCreateOrder" style="margin-top: 10px;">分配</el-button>
							<el-button v-if="selectedApply != undefined && selectedApply.status == '已排券'" type="danger"
								icon="Close" @click="handleCancelOrder" style="margin-top: 10px;">取消分配</el-button>
						</div>
					</el-card>
				</el-col>
			</el-row>

		</div>
		<!-- 新增弹出框 -->
		<el-dialog title="修改金额" v-model="editVisible" width="300px">
			<el-form ref="formRef" :model="form" label-width="0px">
				<el-form-item>
					<el-input label="金额（万元）" v-model="form.value"></el-input>
				</el-form-item>

			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">取 消</el-button>
					<el-button @click="saveEdit">确 定</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox, UploadUserFile } from 'element-plus';
import type { UploadInstance } from 'element-plus'
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import { getAllApply, getPosition, createOrder, cancelOrder, getOrder, autoCreateOrder } from '../api/index';


const fileList = ref<UploadUserFile[]>([]);

interface Account {
	id: number;
	name: string;
	internal_account_name: string;
	internal_account_code: string;
	external_account_name: string;
	external_account_code: string;
}

interface ApplyItem {
	id: number;
	account: Account;
	status: string;
	opponent: string;
	temporary_opponent: string;
	trader: string;
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

interface BondInfoItem {
	bond_code: string;
	bond_trading_market: string;
	short_name: string;
	maturity: string;
	full_name: string;
	bond_type: string;
	institution_type: string;
	city_debt: string;
	bond_credit: string;
	institution_credit: string;
	remaining_period: string;
	valuation_rate: number;
	callable_bond_type: string;
	bond_market_type: string;
	issue_scale: number;
	bond_tenor: string;
	repayment_principal_mode: string;
	major_underwriter: string;
	guarantee_mode: string;
	guarantee_person: string;
	interest_mode: string;
	face_rate: number;
	underlying_index: string;
	underlying_rate: number;
	issue_yield: number;
	issue_price: number;
	pay_interest_frequency: string;
	accrual_interest_frequency: string;
	rating_agency: string;
	have_credit: boolean;
	same_bonds_in_diff_market: string;
}

interface PositionItem {
	id: number;
	account: Account;
	code: string;
	name: string;
	value: number;
	opponent: string;
	market: string;
	initial_settlement_day: string;
	expiry_settlement_day: string;
	interest_rate: number;
	pledge_type: string;
	status: string;
	bond_info: BondInfoItem;
}

interface AllocationItem {
	id: number;
	position: PositionItem;
	apply: ApplyItem;
	value: number;
}

const applyData = ref<ApplyItem[]>([]);
const positionData = ref<PositionItem[]>([]);
const allocationData = ref<AllocationItem[]>([]);
const uploadRef = ref<UploadInstance>();


const submitUpload = () => {
	ElMessageBox.confirm('确定使用上传文件更新持仓吗？', '提示', {
		type: 'warning'
	}).then(() => {
		uploadRef.value!.submit();
	}).catch(() => { });

}

const uploadSuccess = () => {
	ElMessage.success('更新持仓成功');
	getPositionData();
	uploadRef.value!.clearFiles();

}

const getApplyData = () => {
	getAllApply().then(res => {
		applyData.value = res.data;
	});
};
const getPositionData = () => {
	getPosition().then(res => {
		positionData.value = res.data;
	});
};
getApplyData();
getPositionData();



const filterApplyEmergency = (value: number, row: any) => {
	return row.emergency === value
};
const filterApplyStatus = (value: string, row: any) => {
	return row.status === value
};
const filterPositionInstitutionCredit = (value: string, row: any) => {
	return row.bond_info.institution_credit === value
};
const filterPositionBondCredit = (value: string, row: any) => {
	return row.bond_info.bond_credit === value
};
const filterPositionType = (value: string, row: any) => {
	return row.bond_info.bond_type === value
};
const filterPositionStatus = (value: string, row: any) => {
	return row.status === value
};

const allocation = [];
const selectedApply = ref();

const handleApplySelect = (val: ApplyItem | undefined) => {
	selectedApply.value = val;
	if (!selectedApply.value) {
		allocationData.value = [];
		return;
	}
	if (selectedApply.value.status == '待排券') {
		if (allocation[selectedApply.value.id] == undefined) {
			allocation[selectedApply.value.id] = []
		}
		allocationData.value = allocation[selectedApply.value.id];
	}
	if (selectedApply.value.status == '已排券') {
		getOrder(selectedApply.value.id).then(res => {
			var orders = res.data;
			allocationData.value = [];
			for (var i in orders) {
				var tmp_position = null;
				for (var j in positionData.value) {
					if (positionData.value[j].id == orders[i].position) {
						tmp_position = positionData.value[j];
						break;
					}

				}
				allocationData.value.push({ id: orders[i].id, apply: selectedApply.value, position: tmp_position, value: orders[i].value });
			}
			allocation[selectedApply.value.id] = allocationData.value;
		});

	}

};


const autoManage = () => {
	autoCreateOrder().then(res => {
		var orders = res.data;
		for (var i in applyData.value) {
			if (applyData.value[i].status == '待排券') {
				allocation[applyData.value[i].id] = [];
			}
		}
		for (var i in orders) {
			var tmp_apply = null;
			var tmp_position = null;
			for (var j in applyData.value) {
				if (applyData.value[j].id == orders[i].apply) {
					tmp_apply = applyData.value[j];
					break;
				}

			}
			for (var j in positionData.value) {
				if (positionData.value[j].id == orders[i].position) {
					tmp_position = positionData.value[j];
					break;
				}

			}
			tmp_position.value = tmp_position.value - orders[i].value;
			allocation[tmp_apply.id].push({ id: orders[i].id, apply: tmp_apply, position: tmp_position, value: orders[i].value });
		}
		ElMessage.success('自动排券完成');
	});

};

const handleUse = (index: number, row: any) => {
	if (selectedApply.value == undefined) {
		return;
	}
	allocationData.value = allocation[selectedApply.value.id];
	var used = false;
	var allocation_value = 0;
	for (var item in allocationData.value) {
		if (row.id == allocationData.value[item].id) {
			used = true;
		}
		allocation_value = allocation_value + allocationData.value[item].value;

	}
	if (used) {
		return;
	}

	var last = selectedApply.value.value - allocation_value;
	if (last <= 0) {
		last = 0;
	}
	var p_max = row.value;
	row.value = row.value - Math.min(p_max, last);
	allocationData.value.push({ position: row, apply: selectedApply.value, value: Math.min(p_max, last) });
};

let form = reactive({
	value: 0,
});

const editVisible = ref(false);
let idx: number = -1;
let old_value = 0;
const handleEditAllocation = (index: number, row: any) => {
	idx = index;
	old_value = row.value;
	form.value = row.value;
	editVisible.value = true;

};

const saveEdit = () => {
	allocationData.value[idx].value = form.value;
	allocationData.value[idx].position.value = allocationData.value[idx].position.value + old_value - form.value;
	editVisible.value = false;

};


const handleDelete = (index: number) => {
	ElMessage.success('删除成功');
	allocationData.value[index].position.value = allocationData.value[index].position.value + allocationData.value[index].value;
	allocationData.value.splice(index, 1);
};

const handleCreateOrder = () => {
	ElMessageBox.confirm('确定要分配吗？', '提示', {
		type: 'warning'
	}).then(() => {
		createOrder({ apply: selectedApply.value, allocation: allocationData.value }).then(res => {
			if (res.data === 1) {
				getApplyData();
				getPositionData();
				ElMessage.success('分配成功');
			}
		});


	}).catch(() => { });
};
const handleCancelOrder = () => {
	ElMessageBox.confirm('确定要取消分配吗？', '提示', {
		type: 'warning'
	}).then(() => {
		cancelOrder({ apply: selectedApply.value }).then(res => {
			if (res.data === 1) {
				allocation[selectedApply.value.id] = [];
				getApplyData();
				getPositionData();
				ElMessage.success('取消分配成功');
			}
		});


	}).catch(() => { });
};

</script>

<style scoped>
.card-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
}


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