import React from "react";
import "antd/dist/antd.css";
import {Link,withRouter} from "react-router-dom";
import { Popconfirm, Layout, Menu, Avatar, Row, Col, Icon} from "antd";
import {promiseAjax} from '../common/an';
import { Router,Route} from 'react-router';

const {Header} = Layout;

const SubMenu = Menu.SubMenu;  // 为了使用方便
 class HeaderMenu extends React.Component {

     state = {
     user: '安文楚',
     color: '#3399CC',
    };



     handleLogout = () => {
         // logout();
         console.log('11111111122222');
     };

    render(){

        return (
            <Header>
                <Col span={4}>
                    <div style={{color: "#F0F2F5", height: "31px", width: "120px", fontSize: "20px"}}>
                        12306抢票
                    </div>
                </Col>
                <Col span={20}>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={["0"]}
                        style={{ lineHeight: '64px' }}
                    >
                        <Menu.Item key="1"><Link to={"/buy"}>抢票</Link></Menu.Item>
                        <Menu.Item key="2" style={{marginLeft: "100px"}}>订单查询</Menu.Item>
                        <SubMenu
                            style={{float:"right"}}
                            title={
                                <Avatar style={{ backgroundColor: this.state.color, verticalAlign: 'middle' }} size="large">
                                    {this.state.user}
                                </Avatar>
                            }>
                            <Menu.Item key="setting:1"><Icon type="user" />退出登录</Menu.Item>

                        </SubMenu>
                    </Menu>
                </Col>
            </Header>
        );
    }
};
export default HeaderMenu = withRouter(HeaderMenu)
