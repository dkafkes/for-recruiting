% The Stommel Lab.
%	a procedure to run a two dimensional gyre initially
%	with zero concentration, and whose
%	"southern" boundary (row 1) is set at the beginning
%	of the run to be 10 units. The domain is 50 nodes by 50 nodes,
%	with equal spacing of 50 km (50,000 m). The velocity field is
%	sinsusoidal such that it is rotating
%	counter-clockwise. The maximum velocity is 4 cm/s (.04 m/s).
%

warning off 				% to take care of v4 vs. v5 warnings

%
%
lambda=10^9;  % x dimension of the basin in cm (10000 Km)
b=6.25*10^8;  % y dimension of the basin in cm (6250 Km)
dx=90e5;dy=dx; % grid spacing in cm (90 Km)
%
%
nx=round(lambda/dx); %number of grid points (in x)
ny=round(b/dy);      %number of grid points (in y)
%
%incremental coordinates (cm). These are the coordinates you'll need to
%calculate velocity components u and v. See their use to calculate psi
%below.
y=meshgrid([0:dy:b],[0:dx:lambda]); y=y';
x=meshgrid([0:dx:lambda],[0:dy:b]);
%
%
%These coordinates are for plotting only:
%
x_tr=[0:dx:lambda-dx]./10^5;	% distance vectors for plotting tracer distribution(in km)
y_tr=[0:dy:b-dx]./10^5;
%
x_u=[0:dx:lambda]./10^5;	% distance vectors (in Km) for plotting stream function (psi) and velocity components (u and v)
y_v=[0:dy:b]./10^5;
%
%
% parameter you don't need to touch:
D=2e4;  % depth of the layer
%
%--------------------------------------------------------------------------
% parameters you'll play with:
%--------------------------------------------------------------------------
F=1;    % max wind stress (dyne/cm2)
R=.02; % friction coefficient
%beta= 10e-13; %this is the variation of f (Coriolis) with latitude (y)
%Case A-
beta= 10e-13; %because f is constant
%--------------------------------------------------------------------------
%
%
% 
alfa=(D/R)*beta;
gamma=(F*pi)/(R*b);
A=-(alfa/2)+sqrt((alfa^2)/4+(pi/b)^2);
B=-(alfa/2)-sqrt((alfa^2)/4+(pi/b)^2);
p=(1-exp(B*lambda))/(exp(A*lambda)-exp(B*lambda));
q=1-p;
%

%
for i=1:nx+1
    for j=1:ny+1       
        psi(j,i)=gamma*(b/pi)^2*sin(pi*y(j,i)/b)*(p*exp(A*x(j,i))+q*exp(B*x(j,i))-1);
    end
end
%
%--------------------------------------------------------------------------
% write here your solution for velocity components:
%--------------------------------------------------------------------------
for i=1:nx+1
    for j=1:ny+1
        u(j,i)=gamma*(b/pi)*cos(pi*y(j,i)/b)*(p*exp(A*x(j,i))+q*exp(B*x(j,i))-1);
        v(j,i)=-gamma*(b/pi)^(2)*sin(pi*y(j,i)/b)*(p*A*exp(A*x(j,i))+q*B*exp(B*x(j,i)));
    end
end
%--------------------------------------------------------------------------
%
%

contour(x_u,y_v, psi, 15);
colorbar;
title('Beta= 10e-13, F=1000')
xlabel('km East')
ylabel('km North');

min(min(psi))
min(min(v))
max(max(v))


%comment out the return command below for question 7.

%return

% %--------------------------------------------------------------------------
% % put here your value(s) for diffusivity
% km= 2e10;			        % this value has to be in cm2/s 
% %--------------------------------------------------------------------------
% %
% tyr=3600*24*365.25; 	% various constants
% tot_time=10;            %tot run time in years
% 
% vmax=max(max(v)); umax=max(max(u));	% maximum possible velocities
% 
% dt=0.5*(dx/(umax+vmax+4*km/dx));	% stable time step
% nt=tot_time*tyr/dt;				% number of steps in 10 years
% kx=km*ones(ny+1,nx+1); ky=kx;	% kx, ky over all domain
% c=zeros(ny,nx); cold=c;			% initialize concentrations to zero
% 
% % now calculate weight matrices
% % now calculate weight matrices
% 
% wxm=zeros(ny,nx); wym=wxm; w0=wxm;
% 
% w0=1-kx(1:ny,1:nx)*dt/dx/dx -kx(1:ny,2:nx+1)*dt/dx/dx -ky(1:ny,1:nx)*dt/dy/dy- ky(2:ny+1,1:nx)*dt/dy/dy;
% wxm=kx(1:ny,1:nx)*dt/dx/dx;
% wxp=kx(1:ny,2:nx+1)*dt/dx/dx;
% wym=ky(1:ny,1:nx)*dt/dy/dy;
% wyp=ky(2:ny+1,1:nx)*dt/dy/dy;
% 
% 
% for i=1:ny
%     for j=1:nx
%         if u(i,j)>=0 & u(i,j+1)>=0
%             w0(i,j)=w0(i,j)-u(i,j+1)*dt/dx;
%             wxm(i,j)=wxm(i,j)+u(i,j)*dt/dx;
%         elseif u(i,j)<=0 & u(i,j+1)<=0
%             w0(i,j)=w0(i,j)+u(i,j)*dt/dx;
%             wxp(i,j)=wxp(i,j)-u(i,j+1)*dt/dx;
%         elseif u(i,j)>0 & u(i,j+1)<0
%             wxm(i,j)=wxm(i,j)+u(i,j)*dt/dx;
%             wxp(i,j)=wxp(i,j)-u(i,j+1)*dt/dx;
%         elseif u(i,j)<0 & u(i,j+1)>0
%             w0(i,j)=w0(i,j)-u(i,j+1)*dt/dx+u(i,j)*dt/dx;
%         end
%         
%         if v(i,j)>=0 & v(i+1,j)>=0
%             w0(i,j)=w0(i,j)-v(i+1,j)*dt/dy;
%             wym(i,j)=wym(i,j)+v(i,j)*dt/dy;
%         elseif v(i,j)<=0 & v(i+1,j)<=0
%             w0(i,j)=w0(i,j)+v(i,j)*dt/dy;
%             wyp(i,j)=wyp(i,j)-v(i+1,j)*dt/dy;
%         elseif v(i,j)>0 & v(i+1,j)<0
%             wym(i,j)=wym(i,j)+v(i,j)*dt/dy;
%             wyp(i,j)=wyp(i,j)-v(i+1,j)*dt/dy;
%         elseif v(i,j)<0 & v(i+1,j)>0
%             w0(i,j)=w0(i,j) - v(i+1,j)*dt/dy + v(i,j)*dt/dy;
%         end
%     end
% end
% %
% %
% %Boundary conditions
% wxm(:,1)=0;
% wxp(:,nx)=0;
% wym(1,:)=0;
% wyp(ny,:)=0;
% 
% w0(:,1)=w0(:,1)+ kx(1:ny,1)*dt/dx/dx;  %Western
% w0(:,nx)=w0(:,nx)+ kx(1:ny,nx)*dt/dx/dx;  %Eastern
% w0(1,:)=w0(1,:)+ ky(1,1:nx)*dt/dy/dy;  %South
% w0(ny,:)=w0(ny,:)+ ky(ny,1:nx)*dt/dy/dy;  %North
%     
% V=[0:10];               % contour lines vector   
% oldyear=0;				% our trusty year counter
% oldmonth=0;
% for t=1:nt  				% your main time step loop here
%              cold(1,:)=10*ones(1,nx);	% set boundary conditions (others are zero)
%              newyear=ceil(t*dt/tyr);
%              newmonth=ceil(t*dt/(30*86400));	% time counter
%              if newmonth ~= oldmonth	
%              figure(1);contour(x_tr,y_tr,c,V);colorbar;title(sprintf('year %d day %d',newyear,floor((dt*t)/86400)-((newyear-1)*365)));
%              figure(1);pcolor(x_tr,y_tr,c);colorbar;shading flat;caxis([0 10]);title(sprintf('year %d day %d',newyear,floor((dt*t)/86400)-((newyear-1)*365)));
%              drawnow;
%              oldmonth=newmonth;	% only do once a year
%              end
% 
%         %The main event
%             c(2:ny-1,2:nx-1) = w0(2:ny-1,2:nx-1).*cold(2:ny-1,2:nx-1) +...
%             wxm(2:ny-1,2:nx-1).*cold(2:ny-1,1:nx-2) +...
%             wxp(2:ny-1,2:nx-1).*cold(2:ny-1,3:nx) +...
%             wym(2:ny-1,2:nx-1).*cold(1:ny-2,2:nx-1) +...
%             wyp(2:ny-1,2:nx-1).*cold(3:ny,2:nx-1);	
% 
%         %Western Boundary
%             c(2:ny-1,1) = w0(2:ny-1,1).*cold(2:ny-1,1) +...
%             wxp(2:ny-1,1).*cold(2:ny-1,2) +...
%             wym(2:ny-1,1).*cold(1:ny-2,1) +...
%             wyp(2:ny-1,1).*cold(3:ny,  1);		
% 
%         %Eastern Boundary
%             c(2:ny-1,nx) = w0(2:ny-1,nx).*cold(2:ny-1,nx) +...
%             wxm(2:ny-1,nx).*cold(2:ny-1,nx-1) +...
%             wym(2:ny-1,nx).*cold(1:ny-2,nx) +...
%             wyp(2:ny-1,nx).*cold(3:ny,  nx);		
% 
%         %Southern Boundary
%             c(1,2:nx-1) = w0(1,2:nx-1).*cold(1,2:nx-1) +...
%             wxm(1,2:nx-1).*cold(1,1:nx-2) +...
%             wxp(1,2:nx-1).*cold(1,3:nx) +...
%             wyp(1,2:nx-1).*cold(2,2:nx-1);		
% 
%         %Northern Boundary
%             c(ny,2:nx-1) = w0(ny,2:nx-1).*cold(ny,2:nx-1) +...
%             wxm(ny,2:nx-1).*cold(ny,1:nx-2) +...
%             wxp(ny,2:nx-1).*cold(ny,3:nx) +...
%             wym(ny,2:nx-1).*cold(ny-1,2:nx-1);
% 
% 
%         % The 4 corners
%         %southwestern boundary
%             c(1,1) = w0(1,1).*cold(1,1) +...
%             wxp(1,1).*cold(1,2) +...
%             wyp(1,1).*cold(2,1);		
% 
%         %southeastern boundary
%             c(1,nx) = w0(1,nx).*cold(1,nx) +...
%             wxm(1,nx).*cold(1,nx-1) +...
%             wyp(1,nx).*cold(2,nx);		
% 
%         %northwestern boundary
%             c(ny,1) = w0(ny,1).*cold(ny,1) +...
%             wxp(ny,1).*cold(ny,2) +...
%             wym(ny,1).*cold(ny-1,1);		
% 
%         %southeastern boundary
%             c(ny,nx) = w0(ny,nx).*cold(ny,nx) +...
%             wxm(ny,nx).*cold(ny,nx-1) +...
%             wym(ny,nx).*cold(ny-1,nx);	
% 
%             cold=c;			% update concentration matrix
% end
% 
% 
% 
% 
% 
% 
% 
% 
% 
% 
% 
% 







