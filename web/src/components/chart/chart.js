import FusionCharts from 'fusioncharts';
import charts from 'fusioncharts/fusioncharts.charts';
import ReactFusioncharts from 'react-fusioncharts';
import React from 'react';

charts(FusionCharts);

class Chart extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dataSource: {
        chart: {
          caption: "Your Temperatur Over the Previous Week",
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
          { label: 'Mo', value: 5 },
          { label: 'Tu', value: 7 },
          { label: 'We', value: 10 },
          { label: 'Th', value: 25 },
          { label: 'Fr', value: 50 },
          { label: 'Sa', value: 2 },
          { label: 'Su', value: 1 },
        ],
      },
    };

  }

  componentDidMount = async () => {
    //get data
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
