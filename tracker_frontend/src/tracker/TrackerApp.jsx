import './TrackerApp.css';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import LoginComponent from './LoginComponent';
import LogoutComponent from './LogoutComponent';
import HeaderComponent from './HeaderComponent';
import ErrorComponent from './ErrorComponent';
import AuthProvider, { useAuth } from './security/AuthContext';
import TicketCreateComponent from './TicketCreateComponent';
import TicketAllViewComponent from './TicketAllViewComponent';
import TicketSingleViewComponent from './TicketSingleViewComponent';

function ProtectedRoute({ children }) {
    const authContext = useAuth();
    if (authContext.isAuthenticated) {
        return children;
    }

    return <Navigate to="/" />;
}

export default function TrackerApp() {
    return (
        <div className="TrackerApp">
            <AuthProvider>
                <BrowserRouter>
                    <HeaderComponent />
                    <Routes>
                        <Route path='/' element={<LoginComponent />} />
                        <Route path='/login' element={<LoginComponent />} />

                        <Route path='/newticket' element={
                            <ProtectedRoute>
                                <TicketCreateComponent/>
                            </ProtectedRoute>
                        } />

                        <Route path='/tickets' element={
                            <ProtectedRoute>
                                <TicketAllViewComponent/>
                            </ProtectedRoute>
                        } />

                        <Route path='/tickets/:id' element={
                            <ProtectedRoute>
                                <TicketSingleViewComponent/>
                            </ProtectedRoute>
                        } />

                        <Route path='/logout' element={
                            <LogoutComponent />
                        } />

                        <Route path='*' element={<ErrorComponent />} />

                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </div>
    );
}