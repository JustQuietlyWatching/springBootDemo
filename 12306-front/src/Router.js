import React from 'react'
import {
    BrowserRouter,
    Route,
    Link
} from 'react-router-dom'
import IndexPage from './page/home/IndexPage';
import TestPlaneManage from './page/test-plane/TestPlaneManage';
import {Layout} from "antd";
import HeaderMenu from '../src/page/common/HeaderMenu';

const {Content, Footer } = Layout;

export default class Router extends React.Component {

        render() {
            return (
                <BrowserRouter>
                    <Layout className="layout">
                        <HeaderMenu />
                        <Content style={{padding: 30 }}>
                            <div>
                                <Route path="/index" component={IndexPage}/>
                                <Route path="/buy" component={TestPlaneManage}/>
                            </div>
                        </Content>
                        <Footer style={{textAlign: "center"}}>
                            hahaha ©2018 Created by anwenchu
                        </Footer>
                    </Layout>

                </BrowserRouter>
            )
        }

}