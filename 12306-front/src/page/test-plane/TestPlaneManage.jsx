import React from "react";
import { Card, Layout, DatePicker, Select, Row, Col ,Table, Form, Button, Input,Icon, Checkbox} from "antd";
import "antd/dist/antd.css";
import {
    Link
} from 'react-router-dom'
import {promiseAjax} from "../common/an";


const { Header, Content, Footer } = Layout;


const FormItem = Form.Item;
const Option = Select.Option;

@Form.create()
export default class TestPlaneManage extends React.Component {


    state = {
        stations: [],
        trains: [],
        fromCityValue: '',
        toCityValue: '',
    };

    columns = [
        {
            title: '车次',
            dataIndex: 'trainNo',
            key: 'trainNo',
        },
        {
            title: '出发站',
            dataIndex: 'startStation',
            key: 'startStation',
        },
        {
            title: '到达站',
            dataIndex: 'endStation',
            key: 'endStation',
        },
        {
            title: '出发时间',
            dataIndex: 'startTime',
            key: 'startTime',
        },
        {
            title: '到达时间',
            dataIndex: 'endTime',
            key: 'endTime',
        },
        {
            title: '历时',
            dataIndex: 'longDate',
            key: 'longDate',
        },
        {
            title: '商务座',
            dataIndex: 'specialSeat',
            key: 'specialSeat',
        },
        {
            title: '一等座',
            dataIndex: 'oneSeat',
            key: 'oneSeat',
        },
        {
            title: '二等座',
            dataIndex: 'twoSeat',
            key: 'twoSeat',
        },
        {
            title: '高级软卧',
            dataIndex: 'specialSleep',
            key: 'specialSleep',
        },
        {
            title: '软卧',
            dataIndex: 'softSleep',
            key: 'softSleep',
        },
        {
            title: '动卧',
            dataIndex: 'moveSleep',
            key: 'moveSleep',
        },
        {
            title: '硬卧',
            dataIndex: 'hardSleep',
            key: 'hardSleep',
        },
        {
            title: '软座',
            dataIndex: 'softSeat',
            key: 'softSeat',
        },
        {
            title: '硬座',
            dataIndex: 'hardSeat',
            key: 'hardSeat',
        },
        {
            title: '无座',
            dataIndex: 'noSeat',
            key: 'noSeat',
        },
    ];


    login = () => {
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                promiseAjax.post(`/login/`, values).then((data) => {
                    console.log(data);
                });
            }
        });

    }

    componentWillMount() {
        promiseAjax.get('/station/list').then(rsp => {
            if (rsp.data) {
                this.setState({
                    stations: rsp.data,
                })
            }
        }).finally(() => {

        });
    }
    search = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                values.trainDate = values.trainDate.format('YYYY-MM-DD');
                let params = {
                    ...values,
                };
                promiseAjax.get('/query', params).then(rsp => {
                    this.setState({
                        trains: rsp.data
                    })
                    console.log('rsp.data: ', rsp);
                }).finally(() => {
                    this.setState({
                        gettingUsers: false,
                        queryLoading: false,
                    });
                });
            }
        });


    }


    render() {
        const { getFieldDecorator } = this.props.form;
        const cardStyle = {
            marginBottom: 16,
        };
        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 8},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 16},
            },
        };
        const queryItemLayout = {
            xs: 12,
            md: 8,
            lg: 6,
        };
        const stationList = this.state.stations.map(d => <Option key={d.name}>{d.name}</Option>);
        return(
            <Content>
                <Card title="查询条件" style={cardStyle}>
                    <Form onSubmit={this.search}>
                        <Row gutter={16}>
                            <Col {...queryItemLayout}>
                                <FormItem
                                    {...formItemLayout}
                                    label="出发地">
                                    {getFieldDecorator('fromCity',{
                                        rules: [
                                            {
                                                required: true, message: '请输入出发地!',
                                            },
                                        ],
                                    })(
                                        <Select
                                            mode="combobox"
                                            placeholder="请输入出发地"
                                            notFoundContent=""
                                            // value={this.state.fromCityValue}
                                            style={this.props.style}
                                            defaultActiveFirstOption={false}
                                            showArrow={false}
                                            filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                                            onChange={this.handleChange}
                                        >
                                            {stationList}
                                        </Select>
                                    )}
                                </FormItem>
                            </Col>
                            <Col {...queryItemLayout}>
                                <FormItem
                                    {...formItemLayout}
                                    label="目的地">
                                    {getFieldDecorator('toCity',{
                                        rules: [
                                            {
                                                required: true, message: '请输入目的地!',
                                            },
                                        ],
                                    })(
                                        <Select
                                            mode="combobox"
                                            placeholder="请输入目的地"
                                            notFoundContent=""
                                            // value={this.state.toCityValue}
                                            style={this.props.style}
                                            defaultActiveFirstOption={false}
                                            showArrow={false}
                                            filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                                            onChange={this.handleChange}
                                        >
                                            {stationList}
                                        </Select>
                                    )}
                                </FormItem>
                            </Col>
                            <Col {...queryItemLayout}>
                                <FormItem
                                    {...formItemLayout}
                                    label="出发日期">
                                    {getFieldDecorator('trainDate', {
                                        rules: [
                                            {
                                                required: true, message: '请输入出发日期!',
                                            },
                                        ],
                                    })(
                                        <DatePicker style={{width: '100%'}} format={'YYYY-MM-DD'}/>
                                    )}
                                </FormItem>
                            </Col>
                            <Col {...queryItemLayout}>
                                <FormItem
                                    label=" "
                                    colon={false}
                                    {...formItemLayout}
                                >
                                    <Button type="primary" htmlType="submit"  style={{marginRight: '16px'}}>查询</Button>
                                </FormItem>
                            </Col>
                        </Row>
                    </Form>

                </Card>
                <Card title="查询结果" style={cardStyle}>
                    <Table
                        // loading={loading}
                        size="middle"
                        rowKey={(record) => record.trainNo}
                        columns={this.columns}
                        dataSource={this.state.trains}
                        pagination={false}
                    />
                </Card>
                {/*<div style={{ background: "#fff", padding: 20, minHeight: 450 }}>*/}
                    {/*<div style={{ padding: " 10px 0px 20px 0px" }}>*/}
                        {/*<Button type="primary" onClick={this.login}>登录</Button>*/}
                    {/*</div>*/}
                    {/*<div style={{ padding: " 0px 0px 15px 0px" }}>*/}
                        {/*<Form onSubmit={this.login} className="login-form">*/}
                            {/*<FormItem>*/}
                                {/*{getFieldDecorator('userName', {*/}
                                    {/*rules: [{ required: true, message: 'Please input your username!' }],*/}
                                {/*})(*/}
                                    {/*<Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />*/}
                                {/*)}*/}
                            {/*</FormItem>*/}
                            {/*<FormItem>*/}
                                {/*{getFieldDecorator('password', {*/}
                                    {/*rules: [{ required: true, message: 'Please input your Password!' }],*/}
                                {/*})(*/}
                                    {/*<Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />*/}
                                {/*)}*/}
                            {/*</FormItem>*/}
                            {/*<FormItem>*/}
                                {/*{getFieldDecorator('code', {*/}
                                    {/*rules: [{ required: true, message: 'Please input your code!' }],*/}
                                {/*})(*/}
                                    {/*<Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}  />*/}
                                {/*)}*/}
                            {/*</FormItem>*/}
                            {/*<img src="http://localhost:8082/getcode"/>*/}
                            {/*<FormItem>*/}
                                {/*{getFieldDecorator('remember', {*/}
                                    {/*valuePropName: 'checked',*/}
                                    {/*initialValue: true,*/}
                                {/*})(*/}
                                    {/*<Checkbox>Remember me</Checkbox>*/}
                                {/*)}*/}
                                {/*<a className="login-form-forgot" href="">Forgot password</a>*/}
                                {/*Or <a href="">register now!</a>*/}
                            {/*</FormItem>*/}
                        {/*</Form>*/}
                    {/*</div>*/}
                {/*</div>*/}
            </Content>
        )
    }
}


