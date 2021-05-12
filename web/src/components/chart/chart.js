import FusionCharts from 'fusioncharts';
import charts from 'fusioncharts/fusioncharts.charts';
import ReactFusioncharts from 'react-fusioncharts';
import React from 'react';
import moment from 'moment';


charts(FusionCharts);

class Chart extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			dataSource: {
				chart: {
					caption: "Your Temperatur Over the Previous 5 Entries",
					theme: 'fusion',
					palettecolors: '5b5aaf',
					showBorder: '0',
					drawAnchors: '1',
					canvasbgColor: '#ffffff',
					canvasbgAlpha: '10',
					canvasBorderThickness: '0',
					canvasBorderColor: '#ffffff',
					showAlternateHGridColor: '0',
					bgColor: '#ffffff',
					showCanvasBorder: '0',
					showYAxisValues: '0',
					showXAxisValues: '1',
					divlinecolor: '#ffffff',
					patternAlpha: '50',
					plotGradientColor: '#ffffff',
					drawFullAreaBorder: '0',
					showPlotBorder: '1',
					plotBorderThickness: 1.5,
					toolTipBgColor: '#3e1a82',
					toolTipColor: '#ffffff',
					//Removing default gradient fill from columns
					usePlotGradientColor: '1',
					plotFillAngle: 90,
				},
				data: [
					{ label: 'Mo', value: 37 },
					{ label: 'Tu', value: 37.2 },
					{ label: 'We', value: 38 },
					{ label: 'Th', value: 40 },
					{ label: 'Fr', value: 39 },
					{ label: 'Sa', value: 37.6 },
					{ label: 'Su', value: 37 },
				],
			},
		};

	}

	componentDidMount = () => {
		fetch(`http://localhost:8080/api/v1/symptoms/${this.props.userId}`, {
			method: "GET",
			mode: 'cors',
			headers: {
				'Content-Type': 'application/json',
				'Accepts': 'application/json',
			},
		}).then(async response => {
			let res = await response.json();
			if (res.code === '1') {
				let cleanedData = res.payload.slice(-8).map(obj => {
					let label = moment(obj.createdAt).format('D MMM hh:mm:ss');
					let value = obj.fever;
					return { label, value };
				})
				this.setState(prevState => ({
					dataSource: {
						...prevState.dataSource,
						data: cleanedData,
					},
				}));
			}
		})
			.catch(err => {
				console.log(err);
			});
	};


	render() {
		return (
			<ReactFusioncharts
				type='splinearea'
				width="100%"
				height="60%"
				dataFormat="JSON"
				dataSource={this.state.dataSource}
			/>
		);
	}
}


export default Chart;
