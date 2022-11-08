import request from '../utils/request';

export const getAllApply = () => {
    return request({
        url: '/apply/all',
        method: 'get'
    });
};

export const updateApply = (form: object) => {
    return request({
        url: '/apply/update',
        method: 'post',
        data:form
    });
};

export const deleteApply = (aid: number) => {
    return request({
        url: '/apply/delete',
        method: 'post',
        params: {
            id:aid
        }
    });
};

export const addApply = (form: object) => {
    return request({
        url: '/apply/add',
        method: 'post',
        data:form
    });
};

export const getPosition = () => {
    return request({
        url: '/position/all',
        method: 'get'
    });
};

export const createOrder = (form: object) => {
    return request({
        url: '/order/create',
        method: 'post',
        data:form
    });
};

export const cancelOrder = (form: object) => {
    return request({
        url: '/order/cancel',
        method: 'post',
        data:form
    });
};

export const getOrder = (apply: number) => {
    return request({
        url: '/order/get',
        method: 'get',
        params: {
            apply:apply
        }
    });
};

export const autoCreateOrder = () => {
    return request({
        url: '/order/auto',
        method: 'get'
    });
};

export const getAllOpponent = () => {
    return request({
        url: '/opponent/all',
        method: 'get'
    });
};

export const addOpponent = (form: object) => {
    return request({
        url: '/opponent/add',
        method: 'post',
        data: form
    });
};

export const deleteOpponent = (aid: number) => {
    return request({
        url: '/opponent/delete',
        method: 'post',
        params: {
            id:aid
        }
    });
};

export const updateOpponent = (form: object) => {
    return request({
        url: '/opponent/update',
        method: 'post',
        data:form
    });
};

export const getAllAccount = () => {
    return request({
        url: '/account/all',
        method: 'get'
    });
};

export const getAllIssuer = () => {
    return request({
        url: '/issuer/all',
        method: 'get'
    });
};

