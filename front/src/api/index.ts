import request from '../utils/request';

export const getAllApply = () => {
    return request({
        url: 'http://127.0.0.1:8080/apply/all',
        method: 'get'
    });
};

export const updateApply = (form: object) => {
    return request({
        url: 'http://127.0.0.1:8080/apply/update',
        method: 'post',
        data:form
    });
};

export const deleteApply = (aid: number) => {
    return request({
        url: 'http://127.0.0.1:8080/apply/delete',
        method: 'post',
        params: {
            id:aid
        }
    });
};

export const addApply = (form: object) => {
    return request({
        url: 'http://127.0.0.1:8080/apply/add',
        method: 'post',
        data:form
    });
};

export const getPosition = () => {
    return request({
        url: 'http://127.0.0.1:8080/position/all',
        method: 'get'
    });
};

export const createOrder = (form: object) => {
    return request({
        url: 'http://127.0.0.1:8080/order/create',
        method: 'post',
        data:form
    });
};

export const cancelOrder = (form: object) => {
    return request({
        url: 'http://127.0.0.1:8080/order/cancel',
        method: 'post',
        data:form
    });
};

export const getOrder = (apply: number) => {
    return request({
        url: 'http://127.0.0.1:8080/order/get',
        method: 'get',
        params: {
            apply:apply
        }
    });
};

export const autoCreateOrder = () => {
    return request({
        url: 'http://127.0.0.1:8080/order/auto',
        method: 'get'
    });
};
