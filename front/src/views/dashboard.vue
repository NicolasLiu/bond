<template>
	<div>


	</div>
</template>

<script setup lang="ts" name="dashboard">
import { reactive } from 'vue';


const name = localStorage.getItem('ms_username');
const role: string = name === 'admin' ? '超级管理员' : '普通用户';


</script>


