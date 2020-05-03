
INSERT INTO commons.mail(acronym, content, subject)
VALUES('ENTRPMAIL',
	   'pgpsystemws@gmail.com',
'Email de Empresa');

INSERT INTO commons.mail(acronym, content, subject)
VALUES('TMPPASS',
	'<img src="https://avatars0.githubusercontent.com/u/8259605?s=460&u=05382124c86507e10ab4b3383715c3d6ef0a7adb&v=4" height="100" width="100"  style="margin-bottom: -20px" />
<h4>Sistema Lembretes</h4>
	<p></p>
	<p>Su contraseña de ingreso temporal es <strong>{{password}}</strong></p>
	<p>pueda cambiar su contraseña ingresando a <strong>MiPerfil</strong></p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Lembretes</p>',
'Bienvenido al administrador de Lembretes');

INSERT INTO commons.mail(acronym, content, subject)
VALUES('RECOVRPASS',
	'<img src="https://avatars0.githubusercontent.com/u/8259605?s=460&u=05382124c86507e10ab4b3383715c3d6ef0a7adb&v=4" height="100" width="100"  style="margin-bottom: -20px" />
<h4>Sistema Lembretes</h4>
	<p></p>
	<p>Su nueva contraseña de ingreso es <strong>{{password}}</strong></p>
	<p>pueda cambiar su contraseña ingresando a <strong>MiPerfil</strong></p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Lembretes</p>',
'Recuperación de contraseña de Lembretes');
